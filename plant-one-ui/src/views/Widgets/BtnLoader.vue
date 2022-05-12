<template  lang="pug">
   .btn-loader(:class="{ 'icon': icon }")
      .bounce1
      .bounce2
      .bounce3
</template>

<script>
  export default {
    name: 'btn-loader',
    props: {
      icon: {
        type: Boolean,
        required: false,
        default: false
      }
    },
    data() {
      return {
      }
    },
  }
</script>
<style lang="scss" scoped>
.btn-loader{
    position: absolute;
    left: 0px;
    top: 0px;
    width: 100%;
    height: 100%;
    background: $brand-n4;
    overflow: hidden;
    @include flexbox;
    @include align-items(center);
    @include justify-content(center);

    .bounce1, .bounce2, .bounce3 {
      width: 8px;
      height: 8px;
      background-color: $brand-n8;
      border-radius: 100%;
      margin: 0 4px;
      display: inline-block;
      -webkit-animation: sk-bouncedelay 1.4s infinite ease-in-out both;
      animation: sk-bouncedelay 1.4s infinite ease-in-out both;
    }

    &.icon {
      .bounce1, .bounce2, .bounce3 {
        width: 5px;
        height: 5px;
        margin: 0 1px;
      }
    }

    .bounce1 {
      -webkit-animation-delay: -0.32s;
      animation-delay: -0.32s;
    }

    .bounce2 {
      -webkit-animation-delay: -0.16s;
      animation-delay: -0.16s;
    }

    @-webkit-keyframes sk-bouncedelay {
      0%, 80%, 100% { -webkit-transform: scale(0) }
      40% { -webkit-transform: scale(1.0) }
    }

    @keyframes sk-bouncedelay {
      0%, 80%, 100% { 
        -webkit-transform: scale(0);
        transform: scale(0);
      } 40% { 
        -webkit-transform: scale(1.0);
        transform: scale(1.0);
      }
    }
  }
</style>
