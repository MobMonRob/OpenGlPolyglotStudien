package de.dhbw.rahmlab.openglpolyglot.clibraries;

import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.constant.CConstant;
import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.struct.CField;
import org.graalvm.nativeimage.c.struct.CFieldAddress;
import org.graalvm.nativeimage.c.struct.CPointerTo;
import org.graalvm.nativeimage.c.struct.CStruct;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CFloatPointer;
import org.graalvm.nativeimage.c.type.CIntPointer;
import org.graalvm.nativeimage.c.type.CUnsigned;
import org.graalvm.word.PointerBase;

/**
 * constants, functions and structs of the AssImp (Asset Importer) library
 */
@CContext(Directives.class)
public class AI {

    @CConstant("AI_SUCCESS")
    public static native int SUCCESS();

    @CConstant("aiProcessPreset_TargetRealtime_MaxQuality")
    public static native int processPreset_TargetRealtime_MaxQuality();
    
    @CConstant("aiProcess_GenBoundingBoxes")
    public static native int process_GenBoundingBoxes();

    @CFunction("aiImportFile")
    public static native Scene importFile(CCharPointer pFile, @CUnsigned int pFlags);

    @CFunction("aiTransposeMatrix4")
    public static native void transposeMatrix4(Matrix4x4 matrix);

    @CFunction("aiGetMaterialColor")
    public static native int getMaterialColor(Material pMat, CCharPointer pKey, @CUnsigned int type, @CUnsigned int index, Color4D pOut);

    @CFunction("aiGetMaterialIntegerArray")
    public static native int getMaterialIntegerArray(Material pMat, CCharPointer pKey, @CUnsigned int type, @CUnsigned int index, CIntPointer pOut, @CUnsigned CIntPointer pMax);

    @CFunction("aiGetMaterialFloatArray")
    public static native int getMaterialFloatArray(Material pMat, CCharPointer pKey, @CUnsigned int type, @CUnsigned int index, CFloatPointer pOut, @CUnsigned CIntPointer pMax);

    @CStruct(value = "aiVector3D", addStructKeyword = true)
    public interface Vector3D extends PointerBase {

        @CFieldAddress("x")
        CFloatPointer x();

        @CFieldAddress("y")
        CFloatPointer y();

        @CFieldAddress("z")
        CFloatPointer z();

        Vector3D addressOf(int index);
    }

    @CStruct(value = "aiMatrix4x4", addStructKeyword = true)
    public interface Matrix4x4 extends PointerBase {

        @CField("a1") float a1();
        @CField("a2") float a2();
        @CField("a3") float a3();
        @CField("a4") float a4();

        @CField("b1") float b1();
        @CField("b2") float b2();
        @CField("b3") float b3();
        @CField("b4") float b4();

        @CField("c1") float c1();
        @CField("c2") float c2();
        @CField("c3") float c3();
        @CField("c4") float c4();

        @CField("d1") float d1();
        @CField("d2") float d2();
        @CField("d3") float d3();
        @CField("d4") float d4();
    }

    @CStruct(value = "aiColor4D", addStructKeyword = true)
    public interface Color4D extends PointerBase {

        @CField("r")
        float getR();

        @CField("g")
        float getG();

        @CField("b")
        float getB();

        @CField("a")
        float getA();

        Color4D addressOf(int index);
    }

    @CStruct(value = "aiAABB", addStructKeyword = true)
    public interface AABB extends PointerBase {

        @CFieldAddress("mMin")
        Vector3D getMin();

        @CFieldAddress("mMax")
        Vector3D getMax();
    }

    @CStruct(value = "aiFace", addStructKeyword = true)
    public interface Face extends PointerBase {

        @CField("mNumIndices")
        @CUnsigned int getNumIndices();

        @CField("mIndices")
        @CUnsigned CIntPointer getIndices();

        Face addressOf(int index);
    }

    @CPointerTo(Material.class)
    public interface MaterialPointer extends PointerBase {

        Material read();

        MaterialPointer addressOf(int index);
    }

    @CStruct(value = "aiMaterial", addStructKeyword = true)
    public interface Material extends PointerBase {

        @CField("mProperties")
        PointerBase getMaterialProperty();

        @CField("mNumProperties")
        @CUnsigned int getNumProperties();

        @CField("mNumAllocated")
        @CUnsigned int getNumAllocated();
    }

    @CPointerTo(Mesh.class)
    public interface MeshPointer extends PointerBase {

        Mesh read();

        MeshPointer addressOf(int index);
    }

    @CStruct(value = "aiMesh", addStructKeyword = true)
    public interface Mesh extends PointerBase {

        @CField("mPrimitiveTypes")
        @CUnsigned int getPrimitiveTypes();

        @CField("mNumVertices")
        @CUnsigned int getNumVertices();

        @CField("mNumFaces")
        @CUnsigned int getNumFaces();

        @CField("mVertices")
        Vector3D getVertices();

        @CField("mNormals")
        Vector3D getNormals();

        @CField("mTangents")
        PointerBase getTangents();

        @CField("mBitangents")
        PointerBase getBitangents();

        @CFieldAddress("mColors")
        Color4D getColors();

        @CFieldAddress("mTextureCoords")
        PointerBase getTextureCoords();

        @CFieldAddress("mNumUVComponents")
        CIntPointer getNumUVComponents();

        @CField("mFaces")
        Face getFaces();

        @CField("mNumBones")
        @CUnsigned int getNumBones();

        @CField("mBones")
        PointerBase getBones();

        @CField("mMaterialIndex")
        @CUnsigned int getMaterialIndex();

        @CFieldAddress("mName")
        CCharPointer getName();

        @CField("mNumAnimMeshes")
        @CUnsigned int getNumAnimMeshes();

        @CField("mAnimMeshes")
        PointerBase getAnimMeshes();

        @CField("mMethod")
        int getMethod();

        @CFieldAddress("mAABB")
        AABB getAABB();

        @CField("mTextureCoordsNames")
        PointerBase getTextureCoordsNames();
    }

    @CPointerTo(Node.class)
    public interface NodePointer extends PointerBase {

        Node read();

        NodePointer addressOf(int index);
    }

    @CStruct(value = "aiNode", addStructKeyword = true)
    public interface Node extends PointerBase {
        @CFieldAddress("mName")
        PointerBase getName();

        @CFieldAddress("mTransformation")
        Matrix4x4 getTransformation();

        @CField("mParent")
        Node getParent();

        @CField("mNumChildren")
        @CUnsigned int getNumChildren();

        @CField("mChildren")
        NodePointer getChildren();

        @CField("mNumMeshes")
        @CUnsigned int getNumMeshes();

        @CField("mMeshes")
        CIntPointer getMeshes();

        @CField("mMetaData")
        PointerBase getMetaData();
    }

    @CStruct(value = "aiScene", addStructKeyword = true)
    public interface Scene extends PointerBase {
        
        @CField("mFlags")
        @CUnsigned int getFlags();

        @CField("mRootNode")
        Node getRootNode();

        @CField("mNumMeshes")
        @CUnsigned int getNumMeshes();

        @CField("mMeshes")
        MeshPointer getMeshes();

        @CField("mNumMaterials")
        @CUnsigned int getNumMaterials();

        @CField("mMaterials")
        MaterialPointer getMaterials();

        @CField("mNumAnimations")
        @CUnsigned int getNumAnimations();

        @CField("mAnimations")
        PointerBase getAnimations();

        @CField("mNumTextures")
        @CUnsigned int getNumTextures();

        @CField("mTextures")
        PointerBase getTextures();

        @CField("mNumLights")
        @CUnsigned int getNumLights();

        @CField("mLights")
        PointerBase getLights();

        @CField("mNumCameras")
        @CUnsigned int getNumCameras();

        @CField("mCameras")
        PointerBase getCameras();

        @CField("mMetaData")
        PointerBase getMetaData();

        @CFieldAddress("mName")
        PointerBase getName();
    }
}
