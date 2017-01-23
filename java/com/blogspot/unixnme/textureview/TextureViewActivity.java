package com.blogspot.unixnme.textureview;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.TextureView;

import java.io.IOException;

public class TextureViewActivity extends Activity implements TextureView.SurfaceTextureListener {
    private Camera mCamera;
    private TextureView mTextureView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTextureView = new TextureView(this);
        mTextureView.setSurfaceTextureListener(this);
        setContentView(mTextureView);
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mCamera = Camera.open();
        if (mCamera != null) {
            try {
                mCamera.setDisplayOrientation(90);
                mCamera.setPreviewTexture(surface);
                mCamera.startPreview();
            } catch (IOException ioe) {
                // Something bad happened
            }
        }
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // Ignored, Camera does all the work for us
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (mCamera == null)
            return false;

        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // Invoked every time there's a new Camera preview frame
    }
}
