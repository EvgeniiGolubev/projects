package com.company.statistic.event;

/**
 * Событя:
 * - повар приготовил заказ
 * - выбрали набор видео-роликов для заказа
 * - нет ни одного видео-ролика, который можно показать во время приготовления заказа
 */
public enum EventType {
    COOKED_ORDER, SELECTED_VIDEOS, NO_AVAILABLE_VIDEO
}
