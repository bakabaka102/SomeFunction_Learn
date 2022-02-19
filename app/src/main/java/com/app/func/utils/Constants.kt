package com.app.func.utils


object Constants {
    const val CODE_RESULT_SUCCESS_1 = 1
    const val CODE_RESULT_SUCCESS_200 = 200
    const val DURATION_500 = 500L
    const val ALPHA_06 = 0.6F
    const val ALPHA_00 = 0.0F
    const val DURATION_1000 = 1000
    const val ONE_HOUR = 60 * 60 * 1000
    const val TIME_24_HOUR = 24 * 60 * 60 * 1000
    const val SECOND_PER_MINUTE = 60
    const val MILLIS_TO_SECOND = 1000
    const val MILLIS_TO_MIN = 60_000
    const val DECEMBER_CALENDAR = 11

    const val NEGATIVE_ONE_LONG = -1L
    const val NEGATIVE_ONE_INT = -1
    const val ACTION_NAVIGATE_ETOP_500 = -100
    const val ACTION_VERIFY_ETOP_DATING = -101
    const val INT_ZERO = 0
    const val INT_ONE = 1
    const val INT_TWO = 2
    const val INT_THREE = 3
    const val INT_FOUR = 4
    const val INT_TEN = 10
    const val INT_FIVE = 5
    const val LONG_ZERO = 0L
    const val FLOAT_ZERO = 0F
    const val DOUBLE_ZERO = 0.0
    const val DOUBLE_ONE = 1.0
    const val LONG_ONE = 1L
    const val FLOAT_ONE = 1F
    const val STRING_ZERO = "0"
    const val STRING_TRUE = "true"
    const val DEFAULT_SIZE = 10
    const val DEFAULT_PAGE_SIZE = 10
    const val BETA_SCREEN_PRE_LOAD = 3
    const val PAGE_SIZE_DEFAULT = 20
    const val PAGE_SIZE_HIGHLIGHT_DEFAULT = 5
    const val DEFAULT_VALUE = -1
    const val DEFAULT_VALUE_FLOAT = -1F
    const val POSITION_WIDGET_NEWS = 998
    const val POSITION_WIDGET_STAR_AVE = 999
    const val DEFAULT_VALUE_LONG = -1L
    const val DEFAULT_CARD_ELEVATION_TOOLBAR = 12F
    const val PAGE_SIZE_DEFAULT_LONG = 20L
    const val DEFAULT_VALUE_DOUBLE = -1.0

    const val GENDER_MALE = "M"
    const val GENDER_FEMALE = "F"

    const val REGEX_FIT_SPECIAL_CHARACTER = "[^A-Za-zÀ-ỹ0-9\\-_ ]"
    const val REGEX_FIT_SPECIAL_SYMBOL = "[^A-Za-zÀ-ỹ0-9\\ ]"
    const val REGEX_SYMBOL_SUPPER_SPECIAL= "[-_÷π×]"
    const val REGEX_LOWER_NORMAL= "[a-z ]"
    const val REGEX_VI_ACCENTED= "[àảãáạăằẳẵắặâầẩẫấậđèẻẽéẹêềểễếệìỉĩíịòỏõóọôồổỗốộơờởỡớợùủũúụưừửữứựỳỷỹýỵ]"
    const val REGEX_FIT_NUMBER = ".*\\d.*"

    const val SHA256_ALGORITHM_ENCRYPT = "SHA-256"
    const val SHA256_SIGNUM = 1
    const val SHA256_RADIX = 16
    const val SHA256_FIXED_LENGTH = 32
    const val KEY_ENCRYPT = "KAIoT@1234"
    const val KEY_ENCRYPT_EMAIL = "Pe7e4P"
    const val STRING_EMPTY = ""
    const val STRING_BLANK = ""
    const val STRING_DOT = "."
    const val STRING_SPACE = " "
    const val STRING_UNDERLINE = "_"
    const val STRING_DASH = "-"
    const val CHAR_EMPTY = ' '
    const val MENTION_PREFIX = '@'
    const val MENTION_START = "@["
    const val MENTION_SEPARATOR = '|'
    const val MENTION_END = ']'
    const val STRING_ROUND_BRACKET_OPEN = "("
    const val CHAR_ROUND_BRACKET_OPEN = '('
    const val CHAR_ROUND_BRACKET_CLOSE = ')'
    const val DEFAULT_WEB_MIME_TYPE = "text/html; charset=utf-8"
    const val WEB_MIME_TYPE_TEXT_HTML = "text/html"
    const val DATING_TYPE_TEXT = "text"
    const val DATING_TYPE_IMAGES = "images"
    const val DATING_TYPE_EMOJI = "emoji"
    const val DEFAULT_WEB_ENCODING = "UTF-8"
    const val DEFAULT_LANGUAGE = "vi"
    const val ENGLISH_LANGUAGE = "en"
    const val VIETNAM_LANGUAGE = "vi"
    const val POSITION_VI = 0
    const val POSITION_EN = 1
    const val DEFAULT_TIME_TIME_TRACKING_HOUR_FORMAT = "#.##"
    const val KEY_SPLITTER = "splitter"
    const val KEY_UNDER_LINE = "_"

    const val TIME_SHOW_DIALOG_CMTGD = "2021-02-08T00:00:00Z"
    const val TIME_SHOW_DIALOG_CMTGD_END = "2021-02-17T23:59:59Z"

    const val COMPANY_ID_FHO = "01"

    const val ALPHA_PRESSED = 0.4f
    const val ALPHA_50 = 0.5f
    const val ALPHA_NORMAL = 1f
    const val ALPHA_TRANSPARENT = 0f
    const val ANIM_TIME_SHORT = 100L

    const val ALPHA_ENABLED = 1f
    const val ALPHA_65 = 0.65f
    const val ALPHA_DISABLED = 0.4f
    const val ALPHA_MINIMUM = 0.1f

    const val TIME_VIBRATE = 500L
    const val TIME_DELAY = 50L

    const val TIME_DELAY_50 = 50L

    const val SIZE = "10"
    const val PAGE = "1"

    const val DEFAULT_GOLD_REDEEM_CASH = 100
    const val REGEX_REDEEM = "[^\\d]"


    const val DESC = "desc"
    const val ASC = "asc"

    const val DURATION_ANIMATION_ARROW = 100L
    const val DEFAULT_ROTATION_ARROW = 0f
    const val EXPAND_ROTATION_ARROW = 90f
    const val REVERSE_ROTATION_ARROW = 180f


    const val VALUE_SPLIT_PATH = "/"
    const val INDEX_NOT_FOUND = -1
    const val INDEX_CREATE_LIST_POINT = -2

    const val TYPE_LINK_HTTP = "http://"
    const val TYPE_LINK_HTTPS = "https://"

    const val KEY_STRING_NAME = "string"

    const val PATTERN_FORMAT_STRING_TYPE = "%s %s"
    const val ONE_SECOND = 60
    const val QUALITY_IMAGE = 100

    // screen size
    const val SCREEN_SIZE_LOW = "LOW"
    const val SCREEN_SIZE_MEDIUM = "MEDIUM"
    const val SCREEN_SIZE_HIGH = "HIGH"
    const val SCREEN_SIZE_XHIGH = "XHIGH"
    const val SCREEN_SIZE_XXHIGH = "XXHIGH"
    const val SCREEN_SIZE_XXXHIGH = "XXXHIGH"

    const val SEPARATOR_CITY = ", "
    const val SEPARATOR_AGE = " - "
    const val CONFIRM_MAIL_BACK_AUTH = "FORGET PASSWORD BACK AUTH"
    const val FIRST_POSITION_VIEWPAGER = 0

    const val EntitiesID = "DEVICE"
    const val INT_20 = 20
    const val INT_1000 = 1000
    const val WARNING_ERROR_NORMAL = "ER00000"
    const val MESSAGE_NOT_INTERNET =
        "Unable to resolve host \"kangarooshopping.vn\": No address associated with hostname"
    const val MESSAGE_UNAUTHORIZED = "{\"code\":401}"
    const val MESSAGE_REGISTERED_EMAIL = "{\"code\":400}"
    const val PASSWORD_PATTERN_SELECT_FOUR =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[^A-Za-zÀ-ȕ0-9\\-_]).{8,20}$"
    const val PASSWORD_PATTERN_SELECT_THREE =
        "^(?=.*[0-9])(?=.*[A-Z])(?=.*[^A-Za-zÀ-ȕ0-9\\-_]).{8,20}$"
    const val PASSWORD_PATTERN_SELECT_TWO =
        "^(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-zÀ-ȕ0-9\\-_]).{8,20}$"
    const val PASSWORD_PATTERN_SELECT_ONE = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,20}$"
    const val LIMIT_CHARACTER = 255
    const val CHANGE_PASSWORD_BACK_PROFILE_SCREEN = "CHANGE_PASSWORD_BACK_PROFILE_SCREEN"
    const val CHANGE_USER_PROFILE = "CHANGE_USER_PROFILE"
    const val DEFAULT_SUPPORT_PHONE_NUMBER = "1900555566"
    const val MINIUM_NUMBER_CHARACTERS = 7
    const val MINIUM_NUMBER_CHARACTERS_LOGIN = 8
    const val EMAIL_EXIST =
        "No such entity with %fieldName = %fieldValue, %field2Name = %field2Value"


    // BLE connection
    const val DEFAULT_COMMAND_TIME_OUT = 30_000L
    const val DEFAULT_DELAY_SEND_COMMAND = 1000L

    //Setting report
    const val deviceId = "cad763a0-dfff-11eb-afdf-f1ca0efe12dd"
    const val KEY_TEMP_TODAY = "KEY_TEMP_TODAY"
    const val KEY_POW_TODAY = "KEY_POW_TODAY"
    const val KEY_TEMP_SEVEN = "KEY_TEMP_SEVEN"
    const val KEY_POW_SEVEN = "KEY_POW_SEVEN"
    const val KEY_TEMP_FOUR = "KEY_TEMP_FOUR"
    const val KEY_POW_FOUR = "KEY_POW_FOUR"
    const val KEY_TEMP_CUS = "KEY_TEMP_CUS"
    const val KEY_POW_CUS = "KEY_POW_CUS"
    const val KEY_POSITION_TAB = "KEY_POSITION_TAB"
    const val KEY_TIME_START = "KEY_TIME_START"
    const val KEY_TIME_END = "KEY_TIME_END"
    const val KEY_MODEL_NAME = "KEY_MODEL_NAME"
    const val POSITION_TAB_TEMP = 0
    const val POSITION_TAB_POWER = 1

    //Command error
    const val CODE_MSG_0 = "MSG_0"
    const val CODE_MSG_O = "MSG_O"
    const val CODE_MSG_1 = "MSG_1"
    const val CODE_MSG_2 = "MSG_2"
    const val CODE_MSG_3 = "MSG_3"
    const val CODE_MSG_4 = "MSG_4"

    const val SETTING_COMMAND_STATUS_SUCCESS = "1"
    const val INTENT_FILTER_CLOUD_MSG = "firebase-cloud-msg"
    const val NOTI_CHANNEL_ID = "KAIoT"
    const val NOTI_CHANNEL_NAME = "KAIoT channel"
    const val NOTI_CHANNEL_DES = "KAIoT notification channel"
    const val KEY_PUT_EXTRA_ERROR_NOTIFICATION = "KEY_PUT_EXTRA_ERROR_NOTIFICATION"
    const val KEY_EXTRA_ERROR_NOTIFICATION = "KEY_EXTRA_ERROR_NOTIFICATION"

    const val KEY_CURRENT_MODEL = "KGA5009"

    const val AUTH_API = "Bearer"
    const val MAX_CHARACTER_NUMBER_PHONE = 15
    const val MIN_CHARACTER_NUMBER_PHONE = 8

    const val RES_NUMBER_PHONE = "NUMBER_PHONE"
    const val RES_PASSWORD = "PASSWORD"
    const val RES_NAME = "CUSTOMER_NAME"
    const val RES_HASH_CODE = "HASH_CODE"
    const val CODE_SUCCESS_200 = 200
    const val CODE_ERROR_400 = 400
    const val CODE_ERROR_500 = 500
    const val SERVER_ERROR = "SERVER_ERROR"
    const val REGISTER_ERROR = "REGISTER_ERROR"
    const val USER_EXIST = "USER_EXIST"
    const val PHONE_EXIST = "PHONE_EXIST"
    const val USER_NAME_PASSWORD_INCORRECT = "USER_NAME_PASSWORD_INCORRECT"
    const val USER_NOT_EXIST = "USER_NOT_EXIST"
    const val OTP_SENDING_LIMIT = "OTP_SENDING_LIMIT"
    const val OTP_INVALID = "OTP_INVALID"
    const val OTP_EXPIRED_TIME = "OTP_EXPIRED_TIME"
    const val ERROR_PHONE_E001 = "ERROR_PHONE_E001"
    const val ERROR_E001 = "ERROR_E001"

    const val KEY_CHANGE_PASS_SUCCESS = "KEY_CHANGE_PASS_SUCCESS"
    const val CODE_FORGET_BY_PHONE = 1000
    const val MAX_ROOM_NAME = 30

    // Warning
    const val KEY_THINGS_BOARD_FILTER = "error"
    const val DEFAULT_SERIAL_WATER_PURIFIER = "KG100HED-IOT"
    const val HAS_YELLOW_WARNING = "HAS_YELLOW_WARNING"
    const val HAS_RED_WARNING = "HAS_RED_WARNING"

    // Home
    const val KEY_HOME_ID = "HAS_RED_WARNING"
    const val KEY_LIST_ALL_DEVICE = "KEY_LIST_ALL_DEVICE"
    const val KEY_LIST_ROOM = "KEY_LIST_ROOM"
    const val KEY_LIST_DEVICE_FAVORITE = "KEY_LIST_DEVICE_FAVORITE"
    const val KEY_WATER_PURIFIER = "KG100HED-IOT"
    const val ERR_HOME_010 = "ERR_HOME_010"
    const val ERR_ROOM_005 = "ERR_ROOM_005"

    //
    const val CLASS_ID_PRODUCT = "CLASS_ID_PRODUCT"
    const val CLASS_NAME_PRODUCT = "CLASS_NAME_PRODUCT"
    const val PRODUCT_ON_PAGE = 10
    const val DEFAULT_RECENT_VIEW_ID = "-100"
    const val DEFAULT_LOADING_ITEM_ID = "-1"
}
