<template lang="pug">
.animated.fadeIn.content-container
  ImageCropperModal(
    ref="imageCropperModalRef",
    title="Set profile picture"
    :isCircleCropper="true"
    :isResultImgTypeFile="true"
    @yesCallback="profilePicCropped"
  )

  .form-card.profile-card
    .card-body
      .form-body-container.user-profile
        .user-profile-pic
          .profile-pic-container
            .user-initials
              img(v-if="fMode === 'VIEW' && userProfile.base64Image", :src="userProfile.base64Image")
              img(v-else-if="fMode === 'EDIT' && form.base64Image", :src="form.base64Image")
              span(v-else) {{ getUserInitials }}

            template(v-if="fMode === 'EDIT'")
              span
                a.btn-plant-one-link.sm(@click="$refs.profilePicInput.click()") Change

              input(
                ref="profilePicInput"
                type="file"
                accept=".png,.jpeg,.jpg"
                id="profilePicInput"
                @change="handleProfilePicUpload"
                hidden
              )

        .user-profile-details(v-if="userProfile")
          .form-flex-row(v-if="fMode === 'VIEW'")
            .view.form-flex-col-60
              .user-name {{ userProfile.first_name + ' ' + userProfile.last_name }}

          .form-flex-row.mb-4(v-if="fMode === 'VIEW'")
            .action
              a.btn-plant-one-link.sm.edit(
                @click="editUserProfileDataAction"
              ) Edit

          .field-wrapper.form-flex-row.mt-50(v-if="fMode === 'VIEW'")
            .view.form-flex-col-50
              .form-label Email
              .form-value {{ userProfile.email ? userProfile.email : '-' }}

            .view.form-flex-col-50
              .form-label Date of birth
              .form-value {{ userProfile.dob ? userProfile.dob : '-' }}

            .view.form-flex-col-50
              .form-label Street
              .form-value {{ userProfile.street ? userProfile.street : '-' }}

            .view.form-flex-col-50
              .form-label City
              .form-value {{ userProfile.city ? userProfile.city : '-' }}

            .view.form-flex-col-50
              .form-label Country
              .form-value {{ userProfile.country ? userProfile.country : '-' }}

            .view.form-flex-col-50
              .form-label Postal code
              .form-value {{ userProfile.postal_code ? userProfile.postal_code : '-' }}

          .field-wrapper.form-flex-row(v-else)
            .edit.form-flex-col-50
              .form-label First name
              b-form-input(
                type="text",
                v-model="$v.form.first_name.$model",
                placeholder="Enter first name"
              )

            .edit.form-flex-col-50
              .form-label Last name
              b-form-input(
                type="text",
                v-model="$v.form.last_name.$model",
                placeholder="Enter last name"
              )

            .edit.form-flex-col-50
              .form-label Email
              b-form-input(
                :disabled="true",
                type="text",
                v-model="$v.form.email.$model",
              )

            .edit.form-flex-col-50
              .form-label Date of birth
              b-form-input(
                type="date",
                v-model="$v.form.dob.$model",
                placeholder="Enter dob"
              )

            .edit.form-flex-col-25
              .form-label Street
              b-form-input(
                type="text",
                v-model="$v.form.street.$model",
                placeholder="Enter street"
              )

            .edit.form-flex-col-25
              .form-label City
              b-form-input(
                type="text",
                v-model="$v.form.city.$model",
                placeholder="Enter city"
              )

            .edit.form-flex-col-25
              .form-label Country
              b-form-input(
                type="text",
                v-model="$v.form.country.$model",
                placeholder="Enter country"
              )

            .edit.form-flex-col-25
              .form-label Postal code
              b-form-input(
                type="text",
                v-model="$v.form.postal_code.$model",
                placeholder="Enter postal code"
              )

    .card-footer(v-if="fMode != 'VIEW'")
      button.btn-plant-one-secondary.md(@click="viewUserProfileDataAction") Cancel
      button.btn-plant-one-primary.md(
        :disabled="$v.form.$invalid || isDataSaving",
        @click="formSubmitted()"
      )
        span Save
        BtnLoader(v-if="isDataSaving")
</template>

<script>
import APIs from "@/services";

import ImageCropperModal from "@/views/Widgets/ImageCropperModal"

export default {
  name: "UserProfile",
  components: {
    ImageCropperModal
  },
  validations: {
    form: {
      first_name: {},
      last_name: {},
      email: {},
      dob: {},
      street: {},
      city: {},
      country: {},
      postal_code: {},
    },
  },
  data() {
    return {
      fMode: "VIEW",
      isDataSaving: false,

      selectedImage: null,

      form: {
        user_id: "",
        first_name: "",
        last_name: "",
        email: "",
        dob: "",
        user_role: "",
        username: "",
        street: "",
        city: "",
        country: "",
        postal_code: "",
      },
    };
  },
  computed: {
    userProfile() {
      const vm = this

      return vm.$_.cloneDeep(vm.$store.getters.getCurrentUser)
    },
    getUserInitials() {
      const vm = this

      if (vm.userProfile.first_name) {
        return vm.userProfile.first_name.charAt(0).toUpperCase() + vm.userProfile.last_name.charAt(0).toUpperCase()
      }
      return ''
    }
  },
  methods: {
    viewUserProfileDataAction() {
      const vm = this;

      vm.form = vm.$_.clone(vm.userProfile);
      vm.fMode = "VIEW";
    },

    editUserProfileDataAction() {
      const vm = this;

      vm.form = vm.$_.clone(vm.userProfile);
      vm.fMode = "EDIT";
    },

    handleProfilePicUpload(){
      const vm = this
      const file = vm.$refs.profilePicInput.files[0];
      
      if (file) {
        const reader = new FileReader();
        reader.onload = (e)=>{
          vm.$refs.imageCropperModalRef.croppingImage = e.target.result
        }
        reader.readAsDataURL(file)
        vm.$refs.imageCropperModalRef.showModal()
        vm.$refs.profilePicInput.value = ''
      }
    },

    profilePicCropped(resultProfilePic) {
      const vm = this

      vm.selectedImage = resultProfilePic

      const reader = new FileReader();
        reader.onload = (e)=>{
          vm.form.base64Image = e.target.result
        }
        reader.readAsDataURL(resultProfilePic)

      vm.$refs.imageCropperModalRef.hideModal();
    },

    async formSubmitted() {
      const vm = this

      vm.$v.form.$touch();
      if (vm.$v.form.$invalid) {
        return
      }

      const putDataObj = {
        user_id: vm.userProfile.user_id,
        user_role: vm.userProfile.user_role,
        username: vm.userProfile.username,

        first_name: vm.form.first_name,
        last_name: vm.form.last_name,
        email: vm.userProfile.email,
        dob: vm.form.dob,
        street: vm.form.street,
        city: vm.form.city,
        country: vm.form.country,
        postal_code: vm.form.postal_code,
      }

      const putData = new FormData();
      putData.append("user", new Blob([JSON.stringify(putDataObj)], { type: "application/json" }));
      putData.append("file", vm.selectedImage)

      try {
        vm.isDataSaving = true

        await APIs.updateUser(putData)
        await vm.$store.dispatch("SET_CURRENT_USER_DETAILS", vm.userProfile.user_id);

        vm.$helpers.notify("", "Profile updated successfully", vm.$helpers.notificationWithMsgType.TOASTER_SUCCESS)
      } catch (error) {
        console.error(error);
        vm.$helpers.notify("", "Something went wrong", vm.$helpers.notificationWithMsgType.TOASTER_ERROR)
      } finally {
        vm.isDataSaving = false
        vm.viewUserProfileDataAction()
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.content-container {
  background-color: rgb(33,37,41);
  color: $brand-n1;
  @include flexbox;
  @include flex-direction(column);
  min-height: 100%;
  width: 100%;
  padding: 50px 30px 30px; // top right-left bottom padding
  @include transition-multiple(width 0.3s, margin-left 0.3s);
}
.form-card{
  background-color: rgb(235,236,231);
}
.profile-card {
  margin: 0 auto;
  max-width: 980px;
}

.profile-pic-container {
  overflow: hidden;
}

.user-initials {
  width: 120px;
  height: 120px;

  @include flexbox();
  @include align-items(center);
  @include justify-content(center);
  border: 1px solid $brand-n6;
  border-radius: 50%;

  span {
    @include is-size-h1-l;
    color: $brand-n3;
  }

  img {
    width: 100%;
    border-radius: 50%;
    height: 100%;
  }
}

.user-profile {
  @include flexbox;
  padding-top: 40px;

  & .user-profile-pic {
    @include flex(0 0 20%);
    max-width: 20%;
    position: relative;
  }

  & .user-profile-details {
    @include flex(0 0 80%);
    max-width: 80%;
  }

  & .user-name {
    font-size: 32px;
    line-height: 1.06;
    letter-spacing: 0.18px;
    font-weight: 300;
    color: $brand-n3;
    margin-bottom: 15px;
  }

  & .field-wrapper {
    @include flex-wrap(wrap);
    margin-bottom: 0;

    .view {
      &.form-flex-col-50,
      &.form-flex-col-25 {
        margin-bottom: 40px;
        @include align-items(flex-start);
        @include align-content(flex-start);
        @include align-self(flex-start);
      }
    }

    .edit {
      &.form-flex-col-50,
      &.form-flex-col-25 {
        margin-bottom: 30px;
      }
    }
  }
}
</style>
