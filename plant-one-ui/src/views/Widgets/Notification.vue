<template lang="pug">
transition(name="fade")
  .notification-container.toaster(:class="[notification.messageType]")
    .title-container
      div.icon(:class="[notification.messageType]")
        i.fa-solid(:class="[getFontAwesomeClassName(notification.messageType)]")

      div.text
        span(v-html="notification.text")

      div.close-wrapper(@click="triggerClose(notification)")
        i.fa-solid.fa-xmark
</template>`

<script>
import NotificationStore from "@/store/notifications"

export default {
  props: ["notification"],
  data() {
    return {
      timer: null,
    }
  },
  beforeDestroy() {
    const vm = this
    clearTimeout(vm.timer)
  },
  created() {
    const vm = this
    
    const timeout = vm.notification.hasOwnProperty("timeout") ? vm.notification.timeout : true // if not specified then default it true
    
    if (timeout) {
      const delay = vm.notification.delay || NotificationStore.delay // it not specified then default it to 3 sec
      vm.timer = setTimeout(() => {
        vm.triggerClose(vm.notification)
      }, delay)
    }
  },
  methods: {
    getFontAwesomeClassName(messageType) {
      const vm = this

      switch (messageType) {
        case vm.$helpers.toasterMessageType.SUCCESS: return "fa-check";
        case vm.$helpers.toasterMessageType.ERROR: return "fa-triangle-exclamation";
        case vm.$helpers.toasterMessageType.WARNING: return "fa-circle-exclamation";
        case vm.$helpers.toasterMessageType.INFO: return "fa-circle-info";
        default: return "fa-check";
      }
    },

    triggerClose(notification) {
      const vm = this
      clearTimeout(vm.timer)
      vm.$emit("closeNotification", notification)
    },
  },
}
</script>

<style lang="scss" scoped>
.notification-container {
  @include flexbox;
  @include flex-direction(column);
  border: 1px solid;

  &.toaster{
    margin-top: 20px;
    position: relative;
    width: 320px;
    padding: 15px;
    margin-left: auto;
    border-radius: 2px;
    box-shadow: 0 2px 8px 0 rgba(0,0,0,0.05);
    &.success{
      background-color: #F3F9F5;
      border-color: #CEE8D8;
    }
    &.error{
      background-color: $notification-error-bg-color;
      border-color: $notification-error-border-color
    }
    &.info {
      background-color: #F4F8FE;
      border-color: #D1E3FA;
    }
    &.warning{
      background-color: #FEFAF4;
      border-color: #FAEAD1;
    }
    & .title-container {
      @include flexbox;
      @include flex-flow(row);
      @include align-items(flex-start);
      width: 100%;
      & .icon{
        font-size: 20px;
         &.success{
         color: #0A8D3D;
        }
        &.error{
          color: #FF3D16;
        }
        &.warning{
          color: #FF9E16;
        }
        &.info{
          color: $brand-p1;
        }
      }
      & .close-wrapper{
        margin-left: auto;
        color: $brand-n4;
        font-size: 20px;
        cursor: pointer;
      }
      & .title{
        @include is-size-h4-r;
        color: $brand-n2;
        margin-left: 5px;
      }
      .text {
        @include is-size-h5-r;
        color: $brand-n2;
        margin: 0 5px;
        position: relative;
        top: 1px;
      }
    }
    & .text-container{
        @include is-size-h6-r;
        color: $brand-n2;
        margin-top: 10px;
    }
  }
}

.fade-enter-active, .fade-leave-active {
  @include transition(opacity, 0.5s, ease);
}

.fade-enter, .fade-leave-to {
  opacity: 0;
}
</style>
