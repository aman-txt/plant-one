<template lang="pug">
.auth-section
  .auth-header
    //- img(src="")

  .auth-wrapper
    .auth-title Forgot Password

    .auth-form
      .field-wrapper
        b-form-input(
          type="text", 
          v-model="$v.forgotPasswordForm.email.$model",
          placeholder="Enter email",
          :state="$v.forgotPasswordForm.email.$dirty ? !$v.forgotPasswordForm.email.$error : null"
          aria-describedby="email-error-message"
        )
        b-form-invalid-feedback#email-error-message
          template(v-if="!$v.forgotPasswordForm.email.required") This field is required
          template(v-if="!$v.forgotPasswordForm.email.email") Invalid format

        .forgot-pwd
          a.btn-plant-one-link(@click="redirectToLogin") Go to Login page.

      .auth-action
        button.auth-submit-btn.btn-plant-one-primary.md(:disabled="$v.forgotPasswordForm.$invalid", @click="formSubmitted") Get reset password link
          BtnLoader(v-if="isDataSaving")

  .auth-footer
    .copy-right-text © 2022 Plant One. All Rights Reserved.

</template>

<script>
import APIs from "@/services";
import { required, email } from "vuelidate/lib/validators"

export default {
  name: "forgotPassword",
  validations: {
    forgotPasswordForm: {
      email: { required, email },
    }
  },
  data() {
    return {
      forgotPasswordForm: {
        email: '',
      },
      isDataSaving: false
    }
  },
  created() {
    const vm = this

    vm.forgotPasswordForm.email = vm.$route.query.email ? vm.$route.query.email : ""
  },
  methods: {
    redirectToLogin() {
      const vm = this

      vm.$router.push({ name: "Login" });
    },

    async formSubmitted() {
      let vm = this

      if (vm.$v.forgotPasswordForm.invalid) {
        return;
      }

      // let postData = {
      //   email: vm.forgotPasswordForm.email,
      // }

      let postData = new FormData();
      postData.append("email", vm.forgotPasswordForm.email)

      try {
        vm.isDataSaving = true

        // Call API
        await APIs.forgotPassword(postData)

        vm.$helpers.notify("", "Reset password link is sent to your email.", vm.$helpers.notificationWithMsgType.TOASTER_SUCCESS)

        // vm.$router.push({ 
        //   name: "Login",
        //   query: {
        //     email: vm.forgotPasswordForm.email
        //   }
        // });
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