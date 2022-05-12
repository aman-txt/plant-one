<template lang="pug">
.notifications-wrapper
  Notification(
    v-for="n in notifications",
    :key="n.id",
    :notification="n",
    @closeNotification="removeNotification",
    ref="notification"
  )
</template>

<script>
/* SOURCE: https://jsfiddle.net/Linusborg/wnb6tdg8/ */
import NotificationStore from "@/store/notifications"
import Notification from "@/views/Widgets/Notification"

export default {
  components: {
    Notification,
  },
  data() {
    return {
      notifications: NotificationStore.state
    }
  },
  methods: {
    removeNotification(notification) {
      NotificationStore.removeNotification(notification)
    },

    removeAllNotifications() {
      const vm = this

      const notifications = vm.$refs.notification
      if (notifications && notifications.length > 0) {
        const length = notifications.length

        let k = length
        while(k > 0) { // the last elem would be `length-1`
          notifications[length - k].triggerClose()
          k--
        }
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.notifications-wrapper {
  position: fixed;
  z-index: 9999999;
  bottom: 30px;
  left: 85px;
  padding: 0 0 0 2px; // width is 52px = (50 + 2px)
  width: fit-content;
}
</style>
