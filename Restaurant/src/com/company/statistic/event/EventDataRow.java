package com.company.statistic.event;

import java.util.Date;

/**
 * интерфейс-маркеро, по нему мы определяем, является ли переданный объект событием или нет.
 */
public interface EventDataRow {
    EventType getType();
    Date getDate();
    int getTime();
}
