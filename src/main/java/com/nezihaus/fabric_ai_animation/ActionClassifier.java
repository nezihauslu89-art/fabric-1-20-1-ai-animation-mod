package com.nezihaus.fabric_ai_animation;

public final class ActionClassifier {
    private ActionClassifier() {
    }

    public static String classify(AnimationRecorder.PlayerSnapshot snapshot) {
        if (snapshot.swimming()) {
            return "swim";
        }
        if (snapshot.sneaking()) {
            return "crouch";
        }
        if (snapshot.sprinting()) {
            return "run";
        }
        if (snapshot.speed() > 0.18) {
            return "walk";
        }
        if (!snapshot.grounded()) {
            return "jump";
        }
        if (Math.abs(snapshot.yaw()) > 2.0f) {
            return "turn";
        }
        return "idle";
    }
}
