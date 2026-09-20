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
        if (!snapshot.grounded()) {
            return "jump";
        }
        if (snapshot.speed() > 0.22D) {
            return "walk";
        }
        if (snapshot.swingProgress() > 0.05F) {
            return "attack";
        }
        if (Math.abs(snapshot.yaw()) > 2.0F) {
            return "turn";
        }
        return "idle";
    }
}
