package com.earthforge.efcore.feature.animation;

public class EasingUtils {
    public static float ease(float t, AnimationFrame.InterpolationType type) {
        switch (type) {
            case LINEAR:
                return t;
            case EASE_IN_QUAD:
                return t * t;
            case EASE_OUT_QUAD:
                return t * (2 - t);
            case EASE_IN_OUT_QUAD:
                return t < 0.5 ? 2 * t * t : -1 + (4 - 2 * t) * t;
            case EASE_IN_CUBIC:
                return t * t * t;
            case EASE_OUT_CUBIC:
                return --t * t * t + 1;
            case EASE_IN_OUT_CUBIC:
                return t < 0.5 ? 4 * t * t * t : (t - 1) * (2 * t - 2) * (2 * t - 2) + 1;
            case EASE_IN_QUART:
                return t * t * t * t;
            case EASE_OUT_QUART:
                return 1 - --t * t * t * t;
            case EASE_IN_OUT_QUART:
                return t < 0.5 ? 8 * t * t * t * t : 1 - 8 * --t * t * t * t;
            case EASE_IN_QUINT:
                return t * t * t * t * t;
            case EASE_OUT_QUINT:
                return 1 + --t * t * t * t * t;
            case EASE_IN_OUT_QUINT:
                return t < 0.5 ? 16 * t * t * t * t * t : 1 + 16 * --t * t * t * t * t;
            case EASE_IN_EXPO:
                return (float) (t == 0 ? 0 : Math.pow(2, 10 * t - 10));
            case EASE_OUT_EXPO:
                return (float) (t == 1 ? 1 : 1 - Math.pow(2, -10 * t));
            case EASE_IN_OUT_EXPO:
                if (t == 0 || t == 1) return t;
                if (t < 0.5) return (float) (Math.pow(2, 20 * t - 10) / 2);
                return (float) (2 - Math.pow(2, -20 * t + 10) / 2);
            case EASE_IN_CIRC:
                return (float) (1 - Math.sqrt(1 - t * t));
            case EASE_OUT_CIRC:
                return (float) Math.sqrt(1 - --t * t);
            case EASE_IN_OUT_CIRC:
                if (t < 0.5) return (float) (1 - Math.sqrt(1 - 4 * t * t));
                return (float) (Math.sqrt(1 - --t * t) + 1);
            case EASE_IN_BACK:
                return t * t * t - t * (float) Math.sin(t * Math.PI);
            case EASE_OUT_BACK:
                return 1 + --t * t * t + t * (float) Math.sin(t * Math.PI);
            case EASE_IN_OUT_BACK:
                if (t < 0.5) {
                    t *= 2;
                    return 0.5f * (t * t * t - t * (float) Math.sin(t * Math.PI));
                } else {
                    t = 2 * t - 2;
                    return 0.5f * (1 + t * t * t + t * (float) Math.sin(t * Math.PI));
                }
            case EASE_IN_BOUNCE:
                return 1 - ease(1 - t, AnimationFrame.InterpolationType.EASE_OUT_BOUNCE);
            case EASE_OUT_BOUNCE:
                if (t < (float) 4 / 11) {
                    return (121 * t * t) / 16;
                } else if (t < (float) 8 / 11) {
                    return ((float) 363 / 40 * t * t) - ((float) 99 / 10 * t) + (float) 17 / 5;
                } else if (t < (float) 10 / 11) {
                    return ((float) 4356 / 361 * t * t) - ((float) 35442 / 1805 * t) + (float) 16061 / 1805;
                } else {
                    return ((float) 54 / 5 * t * t) - ((float) 513 / 25 * t) + (float) 268 / 25;
                }
            case EASE_IN_OUT_BOUNCE:
                if (t < 0.5) {
                    return 0.5f * ease(2 * t, AnimationFrame.InterpolationType.EASE_IN_BOUNCE);
                } else {
                    return 0.5f * ease(2 * t - 1, AnimationFrame.InterpolationType.EASE_OUT_BOUNCE) + 0.5f;
                }
            case EASE_IN_ELASTIC:
                return (float) (t == 0 ? 0 : Math.pow(2, 10 * t - 10) * Math.sin((t * 10 - 10.75) * (2 * Math.PI / 3)));
            case EASE_OUT_ELASTIC:
                return (float) (t == 1 ? 1 : 1 - Math.pow(2, -10 * t) * Math.sin((t * 10 - 0.75) * (2 * Math.PI / 3)));
            case EASE_IN_OUT_ELASTIC:
                if (t == 0 || t == 1) return t;
                if (t < 0.5) {
                    return (float) (Math.pow(2, 20 * t - 10) * Math.sin((20 * t - 11.125) * (2 * Math.PI / 4)));
                } else {
                    return (float) (1 - Math.pow(2, -20 * t + 10) * Math.sin((20 * t - 11.125) * (2 * Math.PI / 4)));
                }
            case EASE_IN_SINE:
                return (float) (1 - Math.cos(t * Math.PI / 2));
            case EASE_OUT_SINE:
                return (float) Math.sin(t * Math.PI / 2);
            case EASE_IN_OUT_SINE:
                return (float) (0.5 * (1 - Math.cos(Math.PI * t)));
            default:
                return t;
        }
    }
}
