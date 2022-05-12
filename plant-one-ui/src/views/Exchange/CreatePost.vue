<template lang="pug">
.animated.fadeIn
  ImageCropperModal(
    ref="imageCropperModalRef",
    title="Set post picture"
    :isCircleCropper="false"
    :isResultImgTypeFile="true"
    @yesCallback="postPicCropped"
  )

  .content-wrapper
    .breadcrumb-container
      router-link.btn-plant-one-link(:to="{name: 'PostList'}") Exchange
      span.crumb-txt /

    .form-card
      .form-card-header
          .card-title Create post

      .card-body
        .field-wrapper.form-flex-row.image-wrapper
          .image-container
            img.mb-4(v-if="postForm.base64Image", :src="postForm.base64Image")

            input(
              ref="postPicInput"
              type="file"
              accept=".png,.jpeg,.jpg"
              id="postPicInput"
              @change="handlePostPicUpload"
              hidden
            )

            a.btn-plant-one-link.md(@click="$refs.postPicInput.click()")
              template(v-if="postForm.base64Image")
                span Change image

              template(v-else)
                span Add image

        .field-wrapper.form-flex-row
          .edit.form-flex-col-50
            .form-label Plant name
            b-form-input(
              type="text",
              v-model="$v.postForm.title.$model",
              placeholder="Enter plant name",
              :state="$v.postForm.title.$dirty ? !$v.postForm.title.$error : null"
              aria-describedby="title-error-message"
            )
            b-form-invalid-feedback#title-error-message This field is required

          .edit.form-flex-col-50
            .form-label Plant type
            b-form-select(
              v-model="$v.postForm.plantType.$model",
              :options="plantTypeOptions",
              :state="$v.postForm.plantType.$dirty ? !$v.postForm.plantType.$error : null"
              aria-describedby="plant-type-error-message"
            )
            b-form-invalid-feedback#plant-type-error-message This field is required

        .field-wrapper.form-flex-row
          .edit.form-flex-col-50
            .form-label Post type
            b-form-select(
              v-model="$v.postForm.postType.$model",
              @change="handlePostTypeChange($event)",
              :options="postTypeOptions",
              :state="$v.postForm.postType.$dirty ? !$v.postForm.postType.$error : null"
              aria-describedby="post-type-error-message"
            )
            b-form-invalid-feedback#post-type-error-message This field is required

          .edit.form-flex-col-50(v-if="isPostTypeNotAdoption")
            .form-label Price
            b-form-input(
              type="number",
              v-model="$v.postForm.price.$model",
              placeholder="Enter price",
              :state="$v.postForm.price.$dirty ? !$v.postForm.price.$error : null"
              aria-describedby="price-error-message"
            )
            b-form-invalid-feedback#price-error-message This field is required

      .card-footer
        button.btn-plant-one-secondary.md(@click="redirectToListScreen") Cancel
        button.btn-plant-one-primary.md(
          :disabled="$v.postForm.$invalid || isDataSaving",
          @click="formSubmitted()"
        )
          span Save
          BtnLoader(v-if="isDataSaving")

</template>

<script>
import APIs from "@/services";
import { required, requiredIf } from "vuelidate/lib/validators"

import ImageCropperModal from "@/views/Widgets/ImageCropperModal"

export default {
  name: "CreatePost",
  components: {
    ImageCropperModal
  },
  validations: {
    postForm: {
      title: { required },
      postType: { required },
      plantType: { required },
      price: { 
        required: requiredIf(function(form) {
          const vm = this
          const adoptionTypeObj = vm.postTypeOptions.find(o => o.text === "Adoption")
          if (vm.$_.isEmpty(adoptionTypeObj)) {
            return false
          }
          return form.postType !== null && form.postType !== adoptionTypeObj.value
        })
      },
      base64Image: {}
    }
  },
  data() {
    return {
      loggedInUser: {},
      postForm: {
        title: "",
        postType: null,
        plantType: null,
        price: 0,
        base64Image: ""
      },
      plantTypeOptions: [],
      postTypeOptions: [],

      selectedImage: null,

      isDataSaving: false
    }
  },
  created() {
    const vm = this

    vm.loggedInUser = vm.$store.getters.getCurrentUser;

    vm.getInitialData()
  },
  computed: {
    isPostTypeNotAdoption() {
      const vm = this
      
      const adoptionTypeObj = vm.postTypeOptions.find(o => o.text === "Adoption")
      if (vm.$_.isEmpty(adoptionTypeObj)) {
        return false
      }
      return vm.postForm.postType !== null && vm.postForm.postType !== adoptionTypeObj.value
    }
  },
  methods: {
    redirectToListScreen() {
      const vm = this

      vm.$router.push({
        name: "PostList"
      })
    },

    handlePostPicUpload(){
      const vm = this
      const file = vm.$refs.postPicInput.files[0];
      
      if (file) {
        const reader = new FileReader();
        reader.onload = (e)=>{
          vm.$refs.imageCropperModalRef.croppingImage = e.target.result
        }
        reader.readAsDataURL(file)
        vm.$refs.imageCropperModalRef.showModal()
        vm.$refs.postPicInput.value = ''
      }
    },

    postPicCropped(resultPostPic) {
      const vm = this

      vm.selectedImage = resultPostPic

      const reader = new FileReader();
        reader.onload = (e)=>{
          vm.postForm.base64Image = e.target.result
        }
        reader.readAsDataURL(resultPostPic)

      vm.$refs.imageCropperModalRef.hideModal();
    },

    handlePostTypeChange(newValue) {
      const vm = this

      const adoptionTypeObj = vm.postTypeOptions.find(o => o.text === "Adoption")

      if (!vm.$_.isEmpty(adoptionTypeObj) && (newValue === null || newValue === adoptionTypeObj.value)) {
        vm.postForm.price = 0
      }
    },

    async getInitialData() {
      const vm = this

      try {
        const plantTypesResponse = await APIs.getPlantTypes()
        const postTypesResponse = await APIs.getPostTypes()

        vm.plantTypeOptions = plantTypesResponse.data.map(o => { return { value: o.plantTypeId, text: o.plantType } })
        vm.postTypeOptions = postTypesResponse.data.map(o => { return { value: o.postTypeId, text: o.postType } })
        vm.plantTypeOptions.unshift({ value: null, text: "Select plant type" })
        vm.postTypeOptions.unshift({ value: null, text: "Select post type" })
      } catch (error) {
        console.error(error);
        vm.$helpers.notify("", "Something went wrong", vm.$helpers.notificationWithMsgType.TOASTER_ERROR)
      }

    },

    async formSubmitted() {
      const vm = this

      if (vm.$v.postForm.$invalid) return

      const postDataObj = {
        title: vm.postForm.title,
        price: vm.postForm.price,
        plantType: {
          plantTypeId: vm.postForm.plantType,
        },
        postType: {
          postTypeId: vm.postForm.postType,
        },
        seller : {
          user_id: vm.loggedInUser.user_id,
        }
      }

      const postData = new FormData();
      postData.append("post", new Blob([JSON.stringify(postDataObj)], { type: "application/json" }));
      postData.append("file", vm.selectedImage)

      try {
        await APIs.createPost(postData)
        
        vm.$helpers.notify("", "Post created successfully", vm.$helpers.notificationWithMsgType.TOASTER_SUCCESS)
        vm.redirectToListScreen()
      } catch (error) {
        console.error(error);
        vm.$helpers.notify("", "Something went wrong", vm.$helpers.notificationWithMsgType.TOASTER_ERROR)
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.content-wrapper {
  padding: 10px 30px;
}

.form-card {
  margin: 0 auto;
}

.image-wrapper {
  .image-container {
    @include flexbox;
    @include flex-direction(column);
    @include align-items(center);

    width: 100%;
    max-height: 360px;

    img {
      width: auto;
      max-height: calc(360px - 44px);
    }
  }
}
</style>