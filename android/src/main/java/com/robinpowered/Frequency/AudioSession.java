package com.robinpowered.Frequency;

import android.media.AudioTrack;

import com.facebook.react.bridge.Promise;

class AudioSession {
    Promise promise;
    AudioTrack track;
    static final String TRACK_STOPPED_PLAYING = "TRACK_STOPPED_PLAYING";

    AudioSession(AudioTrack track, Promise promise) {
        this.track = track;
        this.promise = promise;
    }

    void cancel() {
        // If track is initialized, stop playback and release resources
        try {
            track.stop();
        } catch (IllegalStateException e) {
            // no-op
        }

        track.release();
        promise.reject(TRACK_STOPPED_PLAYING, "Track stopped playing");
    }

    void complete() {
        track.release();
        promise.resolve(true);
    }

    boolean isPlaying() {
        return track.getState() != AudioTrack.STATE_UNINITIALIZED
                && track.getPlayState() != AudioTrack.PLAYSTATE_STOPPED;
    }
}
