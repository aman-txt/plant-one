import { default as NotificationHelpers } from "./notification"

const notify = NotificationHelpers.notify
const toastMsg = NotificationHelpers.toastMsg
const toasterMessageType = NotificationHelpers.toasterMessageType
const notificationWithMsgType = NotificationHelpers.notificationWithMsgType

// export default functions
export default {
  notify,
  toastMsg,
  toasterMessageType,
  notificationWithMsgType
}