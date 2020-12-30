package com.toocms.tab.base;

import com.toocms.tab.bus.event.SingleLiveEvent;

import java.util.Map;

/**
 * Author：Zero
 * Date：2020/10/30 17:18
 */
final class UIChangeLiveData extends SingleLiveEvent {

    private SingleLiveEvent<Map<String, Object>> showTipEvent;
    private SingleLiveEvent<Void> hideTipEvent;

    private SingleLiveEvent<Map<String, Object>> showItemsDialogEvent;
    private SingleLiveEvent<Map<String, Object>> showSingleActionDialogEvent;
    private SingleLiveEvent<Map<String, Object>> showDialogEvent;

    private SingleLiveEvent<Void> showProgressEvent;
    private SingleLiveEvent<Void> removeProgressEvent;

    private SingleLiveEvent<Void> showEmptyEvent;
    private SingleLiveEvent<Map<String, Object>> showFailedEvent;

    private SingleLiveEvent<Map<String, Object>> startSelectSignAtyEvent;
    private SingleLiveEvent<Map<String, Object>> startSelectMultipleAtyEvent;

    private SingleLiveEvent<Map<String, Object>> startFragmentEvent;
    private SingleLiveEvent<Map<String, Object>> startFragmentForResultEvent;
    private SingleLiveEvent<Void> finishFragmentEvent;
    private SingleLiveEvent<Map<String, Object>> setFragmentResultEvent;

    public SingleLiveEvent<Map<String, Object>> getShowTipEvent() {
        return showTipEvent = createLiveData(showTipEvent);
    }

    public SingleLiveEvent<Void> getHideTipEvent() {
        return hideTipEvent = createLiveData(hideTipEvent);
    }

    public SingleLiveEvent<Map<String, Object>> getShowItemsDialogEvent() {
        return showItemsDialogEvent = createLiveData(showItemsDialogEvent);
    }

    public SingleLiveEvent<Map<String, Object>> getShowSingleActionDialogEvent() {
        return showSingleActionDialogEvent = createLiveData(showSingleActionDialogEvent);
    }

    public SingleLiveEvent<Map<String, Object>> getShowDialogEvent() {
        return showDialogEvent = createLiveData(showDialogEvent);
    }

    public SingleLiveEvent<Void> getShowProgressEvent() {
        return showProgressEvent = createLiveData(showProgressEvent);
    }

    public SingleLiveEvent<Void> getRemoveProgressEvent() {
        return removeProgressEvent = createLiveData(removeProgressEvent);
    }

    public SingleLiveEvent<Void> getShowEmptyEvent() {
        return showEmptyEvent = createLiveData(showEmptyEvent);
    }

    public SingleLiveEvent<Map<String, Object>> getShowFailedEvent() {
        return showFailedEvent = createLiveData(showFailedEvent);
    }

    public SingleLiveEvent<Map<String, Object>> getStartSelectSignAtyEvent() {
        return startSelectSignAtyEvent = createLiveData(startSelectSignAtyEvent);
    }

    public SingleLiveEvent<Map<String, Object>> getStartSelectMultipleAtyEvent() {
        return startSelectMultipleAtyEvent = createLiveData(startSelectMultipleAtyEvent);
    }

    public SingleLiveEvent<Map<String, Object>> getStartFragmentEvent() {
        return startFragmentEvent = createLiveData(startFragmentEvent);
    }

    public SingleLiveEvent<Map<String, Object>> getStartFragmentForResultEvent() {
        return startFragmentForResultEvent = createLiveData(startFragmentForResultEvent);
    }

    public SingleLiveEvent<Void> getFinishFragmentEvent() {
        return finishFragmentEvent = createLiveData(finishFragmentEvent);
    }

    public SingleLiveEvent<Map<String, Object>> getSetFragmentResultEvent() {
        return setFragmentResultEvent = createLiveData(setFragmentResultEvent);
    }

    private <T> SingleLiveEvent<T> createLiveData(SingleLiveEvent<T> liveData) {
        if (liveData == null) {
            liveData = new SingleLiveEvent<>();
        }
        return liveData;
    }

    static final class ParameterField {
        // tip
        public static String TIP_TYPE = "TIP_TYPE";
        public static String TIP_TEXT = "TIP_TEXT";

        // dialog
        public static String DIALOG_TITLE = "DIALOG_TITLE";
        public static String DIALOG_MESSAGE = "DIALOG_MESSAGE";
        public static String DIALOG_LEFT_ACTION_TEXT = "DIALOG_LEFT_ACTION_TEXT";
        public static String DIALOG_LEFT_ACTION_LISTENER = "DIALOG_LEFT_ACTION_LISTENER";
        public static String DIALOG_RIGHT_ACTION_TEXT = "DIALOG_RIGHT_ACTION_TEXT";
        public static String DIALOG_RIGHT_ACTION_LISTENER = "DIALOG_RIGHT_ACTION_LISTENER";
        public static String DIALOG_ITEMS = "DIALOG_ITEMS";
        public static String DIALOG_ITEMS_LISTENER = "DIALOG_ITEMS_LISTENER";

        // picture
        public static String SELECT_PICTURE_MODE = "SELECT_PICTURE_MODE";
        public static String SELECT_PICTURE_RATIO_X = "SELECT_PICTURE_RATIO_X";
        public static String SELECT_PICTURE_RATIO_Y = "SELECT_PICTURE_RATIO_Y";
        public static String SELECT_PICTURE_VIDEO_MAX_SECOND = "SELECT_PICTURE_VIDEO_MAX_SECOND";
        public static String SELECT_PICTURE_RECORD_VIDEO_SECOND = "SELECT_PICTURE_RECORD_VIDEO_SECOND";
        public static String SELECT_PICTURE_SELECTION_MEDIA = "SELECT_PICTURE_SELECTION_MEDIA";
        public static String SELECT_PICTURE_MAX_SELECT_NUM = "SELECT_PICTURE_MAX_SELECT_NUM";
        public static String SELECT_PICTURE_CALLBACK_LISTENER = "SELECT_PICTURE_CALLBACK_LISTENER";

        // fragment
        public static String FRAGMENT = "FRAGMENT";
        public static String BUNDLE = "BUNDLE";
        public static String DESTROY_CURRENT = "DESTROY_CURRENT";
        public static String REQUEST_CODE = "REQUEST_CODE";
        public static String RESULT_CODE = "RESULT_CODE";
        public static String INTENT = "INTENT";

        // failed
        public static String ERROR_TEXT = "ERROR_TEXT";
        public static String ERROR_LISTENER = "ERROR_LISTENER";
    }
}
