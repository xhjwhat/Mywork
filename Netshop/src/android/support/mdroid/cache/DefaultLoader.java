package android.support.mdroid.cache;

import com.netshop.app.NetShopApp;

import android.content.Context;
import android.support.mdroid.cache.ImageFetcher;

public class DefaultLoader extends ImageFetcher {

    public DefaultLoader(Context context) {
        this(context, 0);
    }

    public DefaultLoader(Context context, int imageSize) {
        this(context, imageSize, imageSize);
    }

    public DefaultLoader(Context context, int imageWidth, int imageHeight) {
        super(context, imageWidth, imageHeight);
        init();
    }

    private void init() {
        setImageCache(NetShopApp.getInstance().getImageCache());
    }

}
