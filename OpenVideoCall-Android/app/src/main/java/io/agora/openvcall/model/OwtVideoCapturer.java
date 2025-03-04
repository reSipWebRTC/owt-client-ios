/*
 * Copyright (C) 2018 Intel Corporation
 * SPDX-License-Identifier: Apache-2.0
 */
package io.agora.openvcall.model;

import org.webrtc.Camera1Capturer;
import org.webrtc.Camera1Enumerator;
import org.webrtc.CameraEnumerator;

public final class OwtVideoCapturer extends Camera1Capturer implements VideoCapturer {
    private int width, height, fps;

    private OwtVideoCapturer(String deviceName, boolean captureToTexture) {
        super(deviceName, null, captureToTexture);
    }

    public static OwtVideoCapturer create(int width, int height, int fps, boolean captureToTexture){
        String deviceName = getDeviceName(captureToTexture);
        OwtVideoCapturer capturer = new OwtVideoCapturer(deviceName, captureToTexture);
        capturer.width = width;
        capturer.height = height;
        capturer.fps = fps;
        return capturer;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getFps() {
        return fps;
    }

    @Override
    public Stream.StreamSourceInfo.VideoSourceInfo getVideoSource() {
        return Stream.StreamSourceInfo.VideoSourceInfo.CAMERA;
    }

    public void switchCamera() {
        super.switchCamera(null);
    }

    public void dispose() {
        super.dispose();
    }

    private static String getDeviceName(boolean captureToTexture) {
        CameraEnumerator enumerator = new Camera1Enumerator(captureToTexture);

        String deviceName = null;
        for (String device : enumerator.getDeviceNames()) {
            if (enumerator.isFrontFacing(device)) {
                deviceName = device;
                break;
            }
        }

        return deviceName == null ? enumerator.getDeviceNames()[0] : deviceName;
    }
}
