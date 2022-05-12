<template lang="pug">
  b-modal(
      id="imageCropperModal",
      ref="imageCropperModal",
      :title="title",
      :no-close-on-esc="true",
      :no-close-on-backdrop="true",
      scrollable,
      centered,
      size="md",
  )
    // header container
    template(slot="modal-header")
      slot(name="modalHeader" v-if="hasHeaderSlot")

    // body container
    .body-content-confirm
      // Component used for cropping image
      Cropper(
        v-if="croppingImage"
        :src="croppingImage"
        :backgroundClassname="isTransparentBg ? 'cropper-background': null" 
        :stencilProps="stencilProps"
        :stencilComponent="$options.components[isCircleCropper ? 'CircleStencil' : ' RectangleStencil']"
        @change="onImageChange"
      )
    
    // footer slot
    template(slot="modal-footer")
      button.btn-plant-one-secondary.md(@click='cancelModal') Cancel
      button.btn-plant-one-primary.md(@click='okModal' :disabled="fileUploading") 
        | Save
        BtnLoader(v-if="fileUploading")
  
</template>
<script>
import { Cropper, CircleStencil, RectangleStencil } from 'vue-advanced-cropper'
export default {
  name: 'ImageCropperModal',
  components:{
    Cropper,
    CircleStencil,
    RectangleStencil
  },
  props: {
    title: {
      type: String,
      required: true,
      default: ""
    },
    stencilProps : {
      type: Object,
      required: false
    },
    isCircleCropper: {
      type: Boolean,
      required: false,
      default: true
    },
    isResultImgTypeFile: {
      type: Boolean,
      required: false,
      default: false
    }
  },
  data(){
    return {
      croppingImage:null,
      croppingCoordinates:{
        width: 0,
        height: 0,
        left: 0,
        top:0
      },
      fileUploading: false,
      resultImage: null,
      isTransparentBg: false
    }
  },
  methods: {
    showModal() {
      const vm = this
      vm.croppingCoordinates = {
        width: 0,
        height: 0,
        left: 0,
        top:0
      }
      vm.fileUploading = false
      vm.$refs.imageCropperModal.show()
    },

    hideModal() {
      const vm = this
      vm.fileUploading = false
      vm.croppingImage = null
      vm.$refs.imageCropperModal.hide()
    },

    cancelModal() {
      const vm = this
      vm.hasCancelListener ? vm.$emit("cancel") : vm.hideModal()
    },

    okModal() {
      const vm = this
      vm.fileUploading = true
      if (vm.hasOKListener) {
        vm.isResultImgTypeFile ? vm.resultImage = vm.convertDataURItoFile(vm.resultImage) : null
        vm.$emit("yesCallback", vm.resultImage)
      } else {
        vm.hideModal()
      } 
    },

    // to get result image when someone changes the cropping image area
    onImageChange({coordinates, canvas}){
      const vm = this;

      const context = canvas.getContext("2d")
      let data = context.getImageData(0, 0, canvas.width, canvas.height).data

      vm.checkImgBgTransparency(data)

      // If image background is transparent then fill white background color
      if(vm.isTransparentBg) {
        context.globalCompositeOperation = "destination-over";
        context.fillStyle = "#ffffff";
        context.fillRect(0, 0, context.canvas.width, context.canvas.height);
      }

      vm.croppingCoordinates = coordinates;
      vm.resultImage = canvas.toDataURL('image/jpeg', 0.8)
    },

    /**
     * check if image background is transparent
     */
    checkImgBgTransparency(data) {
      const vm = this

      vm.isTransparentBg = false

      for (var i = 3; i < data.length; i+=4) {
        if (data[i] < 255) {
            vm.isTransparentBg = true
            break
        }
      }
    },

    // function to convert a dataURI image result to the file blob for upload
    convertDataURItoFile(dataURI){
      let byteString;

      if(dataURI.split(',')[0].indexOf('base64') >= 0){
        byteString = atob(dataURI.split(',')[1]);
      } else {
        byteString = unescape(dataURI.split(',')[1]);
      }

      let mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
      let ia = new Uint8Array(byteString.length);
      for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
      }
      return new Blob([ia], {type:mimeString});
    }
  },

  computed: {
    hasHeaderSlot() {
      const vm = this
      return vm.$slots && !!vm.$slots.modalHeader
    },

    hasCancelListener() {
      const vm = this
      return vm.$listeners && vm.$listeners.cancel
    },

    hasOKListener() {
      const vm = this
      return vm.$listeners && vm.$listeners.yesCallback
    }
  }
}
</script>
<style lang="scss">
.cropper-background {
  background: $brand-n8;
}
</style>