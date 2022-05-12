import NotificationStore from "@/store/notifications"

// sample example
/*
vm.$helpers.notify("title goes here","message goes here",
                            vm.$helpers.notificationWithMsgType.TOASTER_SUCCESS)
vm.$helpers.notify("title goes here","message goes here",
                            vm.$helpers.notificationWithMsgType.TOASTER_ERROR)
vm.$helpers.notify("title goes here","message goes here",
                            vm.$helpers.notificationWithMsgType.TOASTER_INFO)
vm.$helpers.notify("title goes here","message goes here",
                            vm.$helpers.notificationWithMsgType.TOASTER_WARNING)
*/

// -------------------------------------------------
const notify = (title = '', msg = '', passedNotificationWithMsgType, delay = 0) => {

  let msgType = toasterMessageType.SUCCESS

  switch (passedNotificationWithMsgType) {
    case notificationWithMsgType.TOASTER_SUCCESS:
      msgType = toasterMessageType.SUCCESS;
      break;

    case notificationWithMsgType.TOASTER_ERROR:
      msgType = toasterMessageType.ERROR;
      break;

    case notificationWithMsgType.TOASTER_WARNING:
      msgType = toasterMessageType.WARNING;
      break;

    case notificationWithMsgType.TOASTER_INFO:
      msgType = toasterMessageType.INFO;
      break;
  }

  showNotify({
    msg: msg,
    title: title,
    messageType: msgType,
    delay: delay
  })
}

const showNotify = ({ title = '', msg = '', messageType = '', delay }) => {
  const data = {
    text: msg,
    title: getTitleOfNotification(title, messageType),
    messageType: messageType,
    delay: delay
  }
  NotificationStore.addNotification(data)
}
const getTitleOfNotification = (title, messageType) => {

  if (title) {
    return title
  }

  switch (messageType) {
    case toasterMessageType.SUCCESS:
      return 'Success'
    case toasterMessageType.ERROR:
      return 'Error'
    case toasterMessageType.WARNING:
      return 'Warning'
    case toasterMessageType.INFO:
      return 'Information'
  }
}

// remove all the notification
const removeAllNotify = () => {
  NotificationStore.removeAllNotifications()
}

const notificationWithMsgType = {
  TOASTER_SUCCESS: 'toaster_success',
  TOASTER_ERROR: 'toaster_error',
  TOASTER_WARNING: 'toaster_warning',
  TOASTER_INFO: 'toaster_info',
}

const toasterMessageType = {
  SUCCESS: 'success',
  ERROR: 'error',
  WARNING: 'warning',
  INFO: 'info'
}

export default {
  notify,
  toasterMessageType,
  notificationWithMsgType,
  removeAllNotify
}