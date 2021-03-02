package com.raphydaphy.cutsceneapi.editor.breakout.timeline.component.helper;

import com.raphydaphy.cutsceneapi.editor.breakout.timeline.component.TimelineScrollBar;
import com.raphydaphy.shaded.org.joml.Vector2f;

public class TimelineScrollBarHelper {

  public static boolean isMouseOverBar(TimelineScrollBar component, Vector2f mousePosition) {
    if (!TimelineViewHelper.isMouseOverComponent(component, mousePosition)) return false;

    float percent = getHoveredPercent(component, mousePosition);
    return percent >= component.getLeftPercent() && percent <= component.getRightPercent();
  }

  public static boolean isMouseOverHandle(TimelineScrollBar component, Vector2f mousePosition, float handlePercent) {
    if (!TimelineViewHelper.isMouseOverComponent(component, mousePosition)) return false;

    Vector2f pos = component.getAbsolutePosition();
    Vector2f size = component.getSize();

    float handleSize = size.y;

    float handlePos = pos.x + (size.x - handleSize) * handlePercent;

    return mousePosition.x >= handlePos && mousePosition.x <= handlePos + handleSize;
  }

  public static boolean isMouseOverEitherHandle(TimelineScrollBar component, Vector2f mousePosition) {
    float[] percents = new float[] {component.getLeftPercent(), component.getRightPercent()};

    for (float percent : percents) {
      boolean overHandle = TimelineScrollBarHelper.isMouseOverHandle(component, mousePosition, percent);
      if (overHandle) return true;
    }

    return false;
  }

  public static float getHoveredPercent(TimelineScrollBar component, Vector2f mousePosition) {
    Vector2f pos = component.getAbsolutePosition();
    float headSize = component.getSize().y;

    float min = 0f;
    float max = 1f;
    float difference = max - min;

    float percentage = (mousePosition.x - pos.x - headSize / 2f) / (component.getSize().x - headSize);
    float value = difference * percentage + min;

    if (value < 0) value = 0;
    else if (value > 1) value = 1;

    return value;
  }

  public static float distanceToPercent(TimelineScrollBar component, float distance) {
    return (distance) / (component.getSize().x);
  }

  public static float percentToDistance(TimelineScrollBar component, float percent) {
    return component.getSize().x * percent;
  }

  public static float percentToAbsolutePos(TimelineScrollBar component, float percent) {
    return component.getAbsolutePosition().x + percentToDistance(component, percent);
  }
}
