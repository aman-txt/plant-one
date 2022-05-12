<template lang="pug">
.auth-section
  .auth-header
    //- img(src="")

  .auth-wrapper
    .auth-title Log in

    .auth-form
      .field-wrapper
        b-form-input(
          type="text", 
          v-model="$v.loginForm.email.$model",
          placeholder="Enter email",
          :state="$v.loginForm.email.$dirty ? !$v.loginForm.email.$error : null"
          aria-describedby="email-error-message"
        )
        b-form-invalid-feedback#email-error-message
          template(v-if="!$v.loginForm.email.required") This field is required
          template(v-if="!$v.loginForm.email.email") Invalid format

      .field-wrapper
        b-form-input(
          type="password", 
          v-model="$v.loginForm.password.$model", 
          placeholder="Enter password",
          :state="$v.loginForm.password.$dirty ? !$v.loginForm.password.$error : null"
          aria-describedby="password-error-message"
        )
        b-form-invalid-feedback#password-error-message This field is required

        .forgot-pwd
          a.btn-plant-one-link(@click="redirectToForgotPassword") forgot password?

      .auth-action
        button.auth-submit-btn.btn-plant-one-primary.md(:disabled="$v.loginForm.$invalid", @click="formSubmitted") Log in
          BtnLoader(v-if="isDataSaving")
      
      .auth-content-box
        span.content New to Plant One?  
        a.btn-plant-one-link.lg(@click="redirectToSignUp") Sign up

  .auth-footer
    .copy-right-text © 2022 Plant One. All Rights Reserved.

</template>

<script>
import APIs from "@/services";
import { required, email } from "vuelidate/lib/validators"

export default {
  name: "Login",
  validations: {
    loginForm: {
      email: { required, email },
      password: { required }
    }
  },
  data() {
    return {
      loginForm: {
        email: '',
        password: ''
      },
      isDataSaving: false
    }
  },
  created() {
    const vm = this

    vm.loginForm.email = vm.$route.query.email ? vm.$route.query.email : ""
  },
  methods: {
    redirectToSignUp() {
      const vm = this
      vm.$router.push({ name: "SignUp" });
    },

    redirectToForgotPassword() {
      const vm = this

      vm.$router.push({ name: "ForgotPassword" });
    },

    async formSubmitted() {
      let vm = this

      if (vm.$v.loginForm.invalid) {
        return;
      }

      let postData = {
        username: vm.loginForm.email,
        password: vm.loginForm.password
      }

      try {
        vm.isDataSaving = true

        // Call API
        const loginResponse = await APIs.login(postData)

        const authInfo = {
          id: loginResponse.data.user_id,
          token: loginResponse.data.jwtToken
        }
        
        // REF :: https://www.npmjs.com/package/vue-cookies
        // $cookies.set(key, value, expireTimes, path, domain, secure, sameSite)
        vm.$cookies.set("authInfo", authInfo, "60MIN", null, null, true)


        await vm.$store.dispatch("SET_CURRENT_USER_DETAILS", loginResponse.data.user_id);

        vm.$helpers.notify("", "Logged in successfully", vm.$helpers.notificationWithMsgType.TOASTER_SUCCESS)

        vm.$router.push({ name: "BlogList" });
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