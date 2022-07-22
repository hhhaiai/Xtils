package me.hhhaiai.xtils.modules;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import me.hhhaiai.xtils.utils.L;

public class XInside implements IXposedHookLoadPackage, IXposedHookZygoteInit, IXposedHookInitPackageResources {

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        L.i("inside initZygote");
    }


    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
        L.i("inside handleInitPackageResources");
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        L.i("inside handleLoadPackage");
        DatasLoaders.loadScheduleder(lpparam);
    }


}
