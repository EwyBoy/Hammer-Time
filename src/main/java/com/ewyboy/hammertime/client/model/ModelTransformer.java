package com.ewyboy.hammertime.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SideOnly(Side.CLIENT)
public class ModelTransformer<M extends ModelBase> {

    private M model;
    private List<ModelRenderer> renderers;
    private List<Tuple<ModelRenderer, List<TexturedQuad>>> quads;

    public ModelTransformer(M model){
        this.model = model;
        this.renderers = this.compileRendererList(model);
        this.quads = this.compileQuadList(this.renderers);
    }

    public List<BakedQuad> getBakedQuads(VertexFormat format, TextureAtlasSprite texture, double scale){
        List<BakedQuad> quads = Lists.newArrayList();

        for(Tuple<ModelRenderer, List<TexturedQuad>> tuple : this.quads){
            quads.addAll(tuple.getSecond().stream().map(quad -> createBakedQuad(format, scale * 0.0625D, tuple.getFirst(), quad, texture)).collect(Collectors.toList()));
        }

        return quads;
    }

    private List<Tuple<ModelRenderer, List<TexturedQuad>>> compileQuadList(List<ModelRenderer> renderers){
        List<Tuple<ModelRenderer, List<TexturedQuad>>> quads = Lists.newArrayList();

        for(ModelRenderer renderer : renderers){
            List<TexturedQuad> rendererQuads = Lists.newArrayList();

            for(ModelBox box : renderer.cubeList){
                Field fieldQuads = null;

                for(Field field : box.getClass().getDeclaredFields()){
                    if(field.getType().isAssignableFrom(TexturedQuad[].class)){
                        fieldQuads = field;
                        break;
                    }
                }

                if(fieldQuads != null){
                    fieldQuads.setAccessible(true);

                    try{
                        Collections.addAll(rendererQuads, (TexturedQuad[])fieldQuads.get(box));
                    }
                    catch(IllegalAccessException e){
                        e.printStackTrace();
                    }
                }
            }

            quads.add(new Tuple<>(renderer, ImmutableList.copyOf(rendererQuads)));
        }

        return ImmutableList.copyOf(quads);
    }

    private List<ModelRenderer> compileRendererList(ModelBase model){
        List<ModelRenderer> renderers = Lists.newArrayList();

        for(Field field : model.getClass().getDeclaredFields()){
            if(field.getType().isAssignableFrom(ModelRenderer.class)){
                field.setAccessible(true);

                try{
                    renderers.add((ModelRenderer)field.get(model));
                }
                catch(IllegalAccessException e){
                    e.printStackTrace();
                }
            }
        }

        return ImmutableList.copyOf(renderers);
    }

    private BakedQuad createBakedQuad(VertexFormat format, double scale, ModelRenderer renderer, TexturedQuad quad, TextureAtlasSprite texture){
        TransformationMatrix matrix = this.getRenderMatrix(renderer, scale);
        Vec3d normal1 = quad.vertexPositions[1].vector3D.subtractReverse(quad.vertexPositions[0].vector3D);
        Vec3d normal2 = quad.vertexPositions[1].vector3D.subtractReverse(quad.vertexPositions[2].vector3D);
        Vec3d normal3 = normal2.crossProduct(normal1).normalize();
        double[] normal = matrix.transform(normal3.xCoord, normal3.yCoord, normal3.zCoord);
        VertexData[] vertexData = new VertexData[quad.vertexPositions.length];

        for(int i = 0; i < vertexData.length; i++){
            PositionTextureVertex vertex = quad.vertexPositions[i];
            double[] pos = matrix.transform(vertex.vector3D.xCoord * scale, vertex.vector3D.yCoord * scale, vertex.vector3D.zCoord * scale);
            float u = texture.getInterpolatedU(vertex.texturePositionX * 16D);
            float v = texture.getInterpolatedV(vertex.texturePositionY * 16D);
            vertexData[i] = new VertexData(format, (float)pos[0], (float)pos[1], (float)pos[2], u, v);
            vertexData[i].setRGBA(1F, 1F, 1F, 1F);
            vertexData[i].setNormal((float)normal[0], (float)normal[1], (float)normal[2]);
        }

        UnpackedBakedQuad.Builder quadBuilder = new UnpackedBakedQuad.Builder(format);

        for(VertexData data : vertexData){
            data.applyVertexData(quadBuilder);
        }

        return quadBuilder.build();
    }

    private TransformationMatrix getRenderMatrix(ModelRenderer renderer, double scale){
        TransformationMatrix matrix = new TransformationMatrix(180D, 1D, 0D, 0D);
        matrix.multiplyRightWith(new TransformationMatrix(8D * scale, -24D * scale, -8D * scale));
        matrix.multiplyRightWith(new TransformationMatrix(
                renderer.offsetX - renderer.rotationPointX * scale,
                renderer.offsetY - renderer.rotationPointY * scale,
                renderer.offsetZ - renderer.rotationPointZ * scale
        ));

        if(renderer.rotateAngleZ != 0){
            matrix.multiplyRightWith(new TransformationMatrix(renderer.rotateAngleZ * (180D / Math.PI), 0D, 0D, 1D));
        }
        if(renderer.rotateAngleY != 0){
            matrix.multiplyRightWith(new TransformationMatrix(renderer.rotateAngleY * (180D / Math.PI), 0D, 1D, 0D));
        }
        if(renderer.rotateAngleX != 0){
            matrix.multiplyRightWith(new TransformationMatrix(renderer.rotateAngleX * (180D / Math.PI), 1D, 0D, 0D));
        }

        return matrix;
    }

    public static class Vector {

        private double x;
        private double y;
        private double z;
        public static final Vector NULL_VECTOR = new Vector(0D, 0D, 0D);

        public Vector(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Vector(Vec3d vec) {
            this.x = vec.xCoord;
            this.y = vec.yCoord;
            this.z = vec.zCoord;
        }

        public Vector(EnumFacing dir, double magnitude) {
            this.x = dir.getFrontOffsetX() * magnitude;
            this.y = dir.getFrontOffsetY() * magnitude;
            this.z = dir.getFrontOffsetZ() * magnitude;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }

        public void setZ(double z) {
            this.z = z;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }

        public Vector add(Vector v) {
            return new Vector(this.x+v.x, this.y+v.y, this.z+v.z);
        }

        public Vector substract(Vector v) {
            return new Vector(this.x-v.x, this.y-v.y, this.z-v.z);
        }

        public Vector scale(double d) {
            Vector v = this.copy();

            if(d!=1) {
                v.x = v.x * d;
                v.y = v.y * d;
                v.z = v.z * d;
            }

            return this;
        }

        public Vector getNormal() {
            Vector normal = new Vector(1D / this.getX(), 0D, -1D / this.getZ());
            normal.normalize();
            return normal;
        }

        public Vector getBiNormal() {
            Vector biNormal = crossProduct(this, this.getNormal());
            biNormal.normalize();
            return  biNormal;
        }

        public Vector normalize() {
            double norm = norm();

            if(norm == 0) {
                return this;
            }

            this.scale(1D / norm());
            return this;
        }

        public double norm() {
            return Math.sqrt(dotProduct(this, this));
        }

        public static double dotProduct(Vector a, Vector b) {
            return a.getX() * b.getX() + a.getY() * b.getY() + a.getZ() * b.getZ();
        }

        public static Vector crossProduct(Vector a, Vector b) {
            double vX = a.y * b.z - a.z * b.y;
            double vY = a.z * b.x - a.x - b.z;
            double vZ = a.x * b.y - a.y *- b.x;
            return new Vector(vX, vY, vZ);
        }

        public Vector projectOn(Vector v) {
            Vector copy = v.copy();
            copy.normalize();
            double norm = dotProduct(this, copy);

            if(norm == 0) {
                return NULL_VECTOR.copy();
            }

            copy.scale(norm);
            return copy;
        }

        public Vector copy() {
            return new Vector(this.x, this.y, this.z);
        }

        public static class UnknownPositionException extends Exception {
            public UnknownPositionException() {
                super("Position not found in NBT !");
            }
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Vector) {
                Vector v = (Vector) obj;
                return v.x == this.x && v.y == this.y && v.z == this.z;
            }

            return false;
        }

    }

    public static class TransformationMatrix {

        private static final int SIZE = 4;
        private static final int ELEMENTS = SIZE * SIZE;
        private double[] matrix;

        public TransformationMatrix() {
            matrix = new double[ELEMENTS];

            for (int i = 0; i < SIZE; i++) {
                set(matrix, i, i, 1);
            }
        }

        public TransformationMatrix(TransformationMatrix other) {
            this.matrix = new double[ELEMENTS];
            System.arraycopy(other.matrix, 0, this.matrix, 0, ELEMENTS);
        }

        public TransformationMatrix(double angle, double x, double y, double z) {
            matrix = new double[ELEMENTS];
            setRotation(angle, x, y, z);
            set(matrix, 3, 3, 1);
        }

        public TransformationMatrix(double x, double y, double z) {
            matrix = new double[ELEMENTS];
            setTranslation(x, y, z);

            for (int i = 0; i < SIZE; i++) {
                set(matrix, i, i, 1);
            }
        }

        public TransformationMatrix(Vec3d translation) {
            this(translation.xCoord, translation.yCoord, translation.zCoord);
        }

        public TransformationMatrix(Vector translation) {
            this(translation.getX(), translation.getY(), translation.getZ());
        }

        public TransformationMatrix(double angle, double x, double y, double z, Vector translation) {
            matrix = new double[ELEMENTS];
            setRotation(angle, x, y, z);
            setTranslation(translation);
            set(matrix, 3, 3, 1);
        }

        private static void set(double[] m, int r, int c, double e) {
            m[c + r * SIZE] = e;
        }

        private static void inc(double[] m, int r, int c, double e) {
            m[c + r * SIZE] += e;
        }

        private static double get(double[] m, int r, int c) {
            return m[c + r * SIZE];
        }

        public void setRotation(double angle, double x, double y, double z) {
            Vector axis = new Vector(x, y, z).normalize();
            angle = Math.toRadians(angle);
            x = axis.getX();
            y = axis.getY();
            z = axis.getZ();
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);
            set(matrix, 0, 0, x * x * (1 - cos) + cos);
            set(matrix, 0, 1, y * x * (1 - cos) - z * sin);
            set(matrix, 0, 2, z * x * (1 - cos) + y * sin);
            set(matrix, 1, 0, x * y * (1 - cos) + z * sin);
            set(matrix, 1, 1, y * y * (1 - cos) + cos);
            set(matrix, 1, 2, y * z * (1 - cos) - x * sin);
            set(matrix, 2, 0, x * z * (1 - cos) - y * sin);
            set(matrix, 2, 1, y * z * (1 - cos) + x * sin);
            set(matrix, 2, 2, z * z * (1 - cos) + cos);
        }

        public void setTranslation(Vector v) {
            this.setTranslation(v.getX(), v.getY(), v.getZ());
        }

        public void setTranslation(double x, double y, double z) {
            set(matrix, 0, 3, x);
            set(matrix, 1, 3, y);
            set(matrix, 2, 3, z);
        }

        public double[] getTranslation() {
            return new double[]{get(matrix, 0, 3), get(matrix, 1, 3), get(matrix, 2, 3)};
        }

        public TransformationMatrix scale(double x, double y, double z) {
            TransformationMatrix m = new TransformationMatrix();
            set(m.matrix, 0, 0, x);
            set(m.matrix, 1, 1, y);
            set(m.matrix, 2, 2, z);
            multiplyRightWith(m);
            return this;
        }

        public TransformationMatrix multiplyLeftWith(TransformationMatrix m) {
            this.matrix = multi(m, this);
            return this;
        }

        public TransformationMatrix multiplyRightWith(TransformationMatrix m) {
            this.matrix = multi(this, m);
            return this;
        }

        private static double[] multi(TransformationMatrix a, TransformationMatrix b) {
            double[] newValues = new double[ELEMENTS];

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    double value = 0;
                    for (int k = 0; k < SIZE; k++) {
                        value += get(a.matrix, i, k) * get(b.matrix, k, j);
                    }
                    set(newValues, i, j, value);
                }
            }

            return newValues;
        }

        public double[] transform(double x, double y, double z) {
            double[] coords = new double[]{x, y, z, 1};
            double[] result = new double[3];

            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < SIZE; j++) {
                    result[i] += get(matrix, i, j) * coords[j];
                }
            }

            return result;
        }

    }

    public static class VertexData {

        private final VertexFormat format;
        private float x, y, z;
        private float u, v;
        private float r, g, b, a;
        private float n_X, n_Y, n_Z;

        public VertexData(VertexFormat format) {
            this.format = format;
        }

        public VertexData(VertexFormat format, float x, float y, float z) {
            this(format);
            this.setXYZ(x, y, z);
        }

        public VertexData(VertexFormat format, float x, float y, float z, float u, float v) {
            this(format, x, y, z);
            this.setUV(u, v);
        }

        public VertexData setXYZ(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }

        public VertexData setUV(float u, float v) {
            this.u = u;
            this.v = v;
            return this;
        }

        public VertexData setRGB(float r, float g, float b) {
            this.r = r;
            this.g = g;
            this.b = b;
            return this;
        }

        public VertexData setRGBA(float r, float g, float b, float a) {
            this.a = a;
            return this.setRGB(r, g, b);
        }

        public VertexData setNormal(float x, float y, float z) {
            this.n_X = x;
            this.n_Y = y;
            this.n_Z = z;
            return this;
        }

        public void applyVertexData(UnpackedBakedQuad.Builder builder) {
            for(int index = 0; index < format.getElementCount(); index++) {
                applyVertexDataForType(index, format.getElement(index).getUsage(), builder);
            }
        }

        private void applyVertexDataForType(int index, VertexFormatElement.EnumUsage type, UnpackedBakedQuad.Builder builder) {
            switch(type) {
                case POSITION:
                    builder.put(index, x, y, z, 1);
                    break;
                case UV:
                    builder.put(index, u, v, 0, 1);
                    break;
                case COLOR:
                    builder.put(index, r, g, b, a);
                    break;
                case NORMAL:
                    builder.put(index, n_X, n_Y, n_Z, 0);
                    break;
                case PADDING:
                    builder.put(index);
                    break;
                case GENERIC:
                    builder.put(index);
            }
        }

    }

}
