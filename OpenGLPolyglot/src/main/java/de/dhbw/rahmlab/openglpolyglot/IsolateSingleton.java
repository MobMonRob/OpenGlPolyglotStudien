package de.dhbw.rahmlab.openglpolyglot;

import com.oracle.svm.core.Uninterruptible;
import com.oracle.svm.core.c.CGlobalData;
import com.oracle.svm.core.c.CGlobalDataFactory;
import com.oracle.svm.core.c.function.CEntryPointActions;
import com.oracle.svm.core.c.function.CEntryPointOptions;
import org.graalvm.nativeimage.Isolates;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.type.WordPointer;

@CContext(Directives.class)
public class IsolateSingleton {

    public static final CGlobalData<WordPointer> isolate = CGlobalDataFactory.createWord();

    public static void initialize() {
        var isolateSingleton = Isolates.getIsolate(Isolates.createIsolate(Isolates.CreateIsolateParameters.getDefault()));
        isolate.get().write(isolateSingleton);
    }

    /**
     * Used as an argument of @CEntryPointOptions to specify that the
     * CEntryPoint should run in the IsolateThread of the IsolateSingleton.
     * With this argument multiple CEntryPoints can share the same thread, and
     * therefore have the same state. Otherwise, accessing the same
     * EuclidViewer3D-Object from multiple CEntryPoints would not be possible.
     */
    public static final class Prologue implements CEntryPointOptions.Prologue {
        @Uninterruptible(reason = "prologue")
        public static void enter() {
            CEntryPointActions.enterAttachThread(isolate.get().read(), true);
        }
    }
}
