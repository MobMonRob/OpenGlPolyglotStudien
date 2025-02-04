package de.dhbw.rahmlab.openglpolyglot.clibraries;

import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.constant.CConstant;
import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.struct.AllowNarrowingCast;
import org.graalvm.nativeimage.c.struct.CField;
import org.graalvm.nativeimage.c.struct.CFieldAddress;
import org.graalvm.nativeimage.c.struct.CStruct;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CDoublePointer;
import org.graalvm.nativeimage.c.type.CFloatPointer;
import org.graalvm.nativeimage.c.type.CIntPointer;
import org.graalvm.nativeimage.c.type.CUnsigned;
import org.graalvm.word.PointerBase;

@CContext(Directives.class)
public class AI {

    @CConstant("aiProcessPreset_TargetRealtime_MaxQuality")
    public static native int processPreset_TargetRealtime_MaxQuality();

    @CFunction("aiImportFile")
    public static native Scene importFile(CCharPointer pFile, @CUnsigned int pFlags);

    @CFunction("aiTransposeMatrix4")
    public static native void transposeMatrix4(Matrix4x4 matrix);

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

        @CFieldAddress("r")
        CFloatPointer r();

        @CFieldAddress("g")
        CFloatPointer g();

        @CFieldAddress("b")
        CFloatPointer b();

        @CFieldAddress("a")
        CFloatPointer a();

        Color4D addressOf(int index);
    }
    
    @CStruct(value = "aiFace", addStructKeyword = true)
    public interface Face extends PointerBase {

        @CField("mNumIndices")
        @CUnsigned int getNumIndices();

        @CField("mIndices")
        @CUnsigned CIntPointer getIndices();

        Face addressOf(int index);
    }

    @CStruct(value = "aiMesh", addStructKeyword = true)
    public interface Mesh extends PointerBase {

        @CField("mPrimitiveTypes")
        int getPrimitiveTypes();

        @CField("mNumVertices")
        int getNumVertices();

        @CField("mNumFaces")
        int getNumFaces();

        @CFieldAddress("mVertices")
        Vector3D getVertices();

        @CFieldAddress("mNormals")
        Vector3D getNormals();

        @CFieldAddress("mTangents")
        PointerBase getTangents();

        @CFieldAddress("mBitangents")
        PointerBase getBitangents();

        @CFieldAddress("mColors")
        Color4D getColors();

        @CFieldAddress("mTextureCoords")
        PointerBase getTextureCoords();

        @CFieldAddress("mNumUVComponents")
        CIntPointer getNumUVComponents();

        @CFieldAddress("mFaces")
        Face getFaces();

        @CField("mNumBones")
        int getNumBones();

        @CFieldAddress("mBones")
        PointerBase getBones();

        @CField("mMaterialIndex")
        int getMaterialIndex();

        //@CField("mName")
        //CCharPointer getName();

        @CField("mNumAnimMeshes")
        int getNumAnimMeshes();

        @CFieldAddress("mAnimMeshes")
        PointerBase getAnimMeshes();

        @CField("mMethod")
        int getMethod();

        //@CField("mAABB")
        //PointerBase getAABB();

        @CFieldAddress("mTextureCoordsNames")
        PointerBase getTextureCoordsNames();

        Mesh addressOf(int index);
    }

    @CStruct(value = "aiNode", addStructKeyword = true)
    public interface Node extends PointerBase {
        //@CField("mName")
        //CCharPointer getName();

        //@CField("mTransformation")
        //Matrix4x4 getTransformation();

        @CField("mParent")
        Node getParent();

        @CField("mNumChildren")
        int getNumChildren();

        @CFieldAddress("mChildren")
        Node getChildren();

        @CField("mNumMeshes")
        int getNumMeshes();

        @CFieldAddress("mMeshes")
        CIntPointer getMeshes();

        @CField("mMetaData")
        PointerBase getMetaData();

        Node addressOf(int index);
    }
    
    @CStruct(value = "aiScene", addStructKeyword = true)
    public interface Scene extends PointerBase {
        
        @CField("mFlags")
        int getFlags();

        @CField("mRootNode")
        Node getRootNode();

        @CField("mNumMeshes")
        int getNumMeshes();

        @CFieldAddress("mMeshes")
        Mesh getMeshes();

        @CField("mNumMaterials")
        int getNumMaterials();

        @CField("mMaterials")
        PointerBase getMaterials();

        @CField("mNumAnimations")
        int getNumAnimations();

        @CField("mAnimations")
        PointerBase getAnimations();

        @CField("mNumTextures")
        int getNumTextures();

        @CField("mTextures")
        PointerBase getTextures();

        @CField("mNumLights")
        int getNumLights();

        @CField("mLights")
        PointerBase getLights();

        @CField("mNumCameras")
        int getNumCameras();

        @CField("mCameras")
        PointerBase getCameras();

        @CField("mMetaData")
        PointerBase getMetaData();

        //@CField("mName")
        //CCharPointer getName();
    }
}
