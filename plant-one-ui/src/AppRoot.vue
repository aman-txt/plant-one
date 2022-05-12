<template lang="pug">
  div.approot
    GlobalNav

    .main-content-wrapper
      router-view
</template>

<script>
import GlobalNav from "@/views/GlobalNav"

export default {
  name: "AppRoot",
  components:{
    GlobalNav
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
.main-content-wrapper {
  position: relative;
  top: 50px;
  height: calc(100vh - 50px);
  overflow: auto;
  @include webkit-scroller;

  background-color: $brand-n7;
}
</style>
