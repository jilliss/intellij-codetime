package com.software.codetime.models;

import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.util.Consumer;
import com.software.codetime.managers.UserSessionManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import swdc.java.ops.manager.FileUtilManager;

import java.awt.event.MouseEvent;
import java.util.logging.Logger;

public class StatusBarKpmTextWidget implements StatusBarWidget {
    public static final Logger log = Logger.getLogger("SoftwareCoStatusBarKpmTextWidget");

    public static final String KPM_TEXT_ID = "software.kpm.text";

    private String msg = "";
    private String tooltip = "";
    private String id;

    private UserSessionManager sessionMgr = UserSessionManager.getInstance();

    private Consumer<MouseEvent> eventHandler;

    private final TextPresentation presentation = new StatusPresentation();

    public StatusBarKpmTextWidget(String id) {
        this.id = id;
        eventHandler = new Consumer<MouseEvent>() {
            @Override
            public void consume(MouseEvent mouseEvent) {
                sessionMgr.statusBarClickHandler();
            }
        };
    }

    public void setText(String msg) {
        this.msg = msg;
    }

    public void setTooltip(String tooltip) {
        String name = FileUtilManager.getItem("name");

        if (tooltip == null) {
            tooltip = "Code time today. Click to see more from Code Time.";
        }

        if (tooltip.lastIndexOf(".") != tooltip.length() - 1) {
            tooltip += ".";
        }

        if (name != null) {
            tooltip += " Logged in as " + name;
        }

        this.tooltip = tooltip;
    }

    class StatusPresentation implements StatusBarWidget.TextPresentation {

        @NotNull
        @Override
        public String getText() {
            return StatusBarKpmTextWidget.this.msg;
        }

        @NotNull
        @Override
        public String getMaxPossibleText() {
            return "";
        }

        @Override
        public float getAlignment() {
            return 0;
        }

        @Nullable
        @Override
        public String getTooltipText() {
            return StatusBarKpmTextWidget.this.tooltip;
        }

        @Nullable
        @Override
        public Consumer<MouseEvent> getClickConsumer() {
            return eventHandler;
        }
    }

    @Override
    public WidgetPresentation getPresentation(@NotNull PlatformType type) {
        return presentation;
    }

    @NotNull
    @Override
    public String ID() {
        return id;
    }

    @Override
    public void install(@NotNull StatusBar statusBar) {
    }

    @Override
    public void dispose() {
    }
}