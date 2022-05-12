<template lang="pug">
  #app
    Notifications(ref="appNotifications")
    router-view
</template>

<script>
import Notifications from "@/views/Widgets/Notifications"

export default {
  name: 'App',
  components: {
    Notifications,
  }
}
</script>

<style lang="scss">
@import "@/assets/styles/app.scss";

#app {
  height: 100vh;
}
</style>
