<template lang="pug">
.auth-section
  .auth-header
    //- img(src="")

  .auth-wrapper
    .auth-title Change Password

    .auth-form
      .field-wrapper
        b-form-input(
          type="password", 
          v-model="$v.changePasswordForm.newPassword.$model", 
          placeholder="Enter new password",
          :state="$v.changePasswordForm.newPassword.$dirty ? !$v.changePasswordForm.newPassword.$error : null"
          aria-describedby="new-password-error-message"
        )
        b-form-invalid-feedback#new-password-error-message This field is required

      .field-wrapper
        b-form-input(
          type="password", 
          v-model="$v.changePasswordForm.confirmPassword.$model", 
          placeholder="Confirm password",
          :state="$v.changePasswordForm.confirmPassword.$dirty ? !$v.changePasswordForm.confirmPassword.$error : null"
          aria-describedby="confirm-password-error-message"
        )
        b-form-invalid-feedback#confirm-password-error-message Password not same as New Password

        .forgot-pwd
          a.btn-plant-one-link(@click="redirectToLogin") Go to Login page.

      .auth-action
        button.auth-submit-btn.btn-plant-one-primary.md(:disabled="$v.changePasswordForm.$invalid", @click="formSubmitted") Save
          BtnLoader(v-if="isDataSaving")

  .auth-footer
    .copy-right-text © 2022 Plant One. All Rights Reserved.

</template>

<script>
import APIs from "@/services";
import { required, sameAs } from "vuelidate/lib/validators"

export default {
  name: "forgotPassword",
  validations: {
    changePasswordForm: {
      token: { required },
      newPassword: { required },
      confirmPassword: { sameAsPassword: sameAs('newPassword') }
    }
  },
  data() {
    return {
      changePasswordForm: {
        token: '',
        newPassword: '',
        confirmPassword: ''
      },
      isDataSaving: false
    }
  },
  created() {
    const vm = this

    vm.changePasswordForm.token = vm.$route.params.token ? vm.$route.params.token : ""
  },
  methods: {
    redirectToLogin() {
      const vm = this

      vm.$router.push({ name: "Login" });
    },

    async formSubmitted() {
      let vm = this

      if (vm.$v.changePasswordForm.invalid) {
        return;
      }

      let postData = {
        token: vm.changePasswordForm.token,
        newPassword: vm.changePasswordForm.newPassword,
      }

      try {
        vm.isDataSaving = true

        // Call API
        await APIs.changePassword(postData)

        vm.$helpers.notify("", "Password changed successfully", vm.$helpers.notificationWithMsgType.TOASTER_SUCCESS)

        vm.$router.push({ name: "Login" });
      } catch (error) {
        console.error(error);
        vm.$helpers.notify("", "Something went wrong", vm.$helpers.notificationWithMsgType.TOASTER_ERROR)
      } finally {
        vm.isDataSaving = false
      }
    }
  }
}
</script>

<style lang="scss" scoped>
</style>