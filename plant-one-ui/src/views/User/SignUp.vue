<template lang="pug">
.auth-section
  .auth-wrapper.auth-container
    .sections-wrapper
      .left-section
        img.plant-one-logo(src="/img/logo/plant-lg.png")
        .plant-one-desc
          | Plant One is platform to make more plants available to people at a cheaper price that will encourage more people to contribute towards the good cause.

        .plant-one-desc
          | This platform is an opportunity for plant enthusiasts to build an online community for exchanging ideas and plants.
        
      .vertical-separator
      
      .right-section
        .auth-title Register to Plant One

        .auth-form
          .field-wrapper
            b-form-input(
              type="text", 
              v-model="$v.signUpForm.email.$model",
              placeholder="Enter email",
              :state="$v.signUpForm.email.$dirty ? !$v.signUpForm.email.$error : null"
              aria-describedby="email-error-message"
            )
            b-form-invalid-feedback#email-error-message
              template(v-if="!$v.signUpForm.email.required") This field is required
              template(v-if="!$v.signUpForm.email.email") Invalid format

          .field-wrapper
            b-form-input(
              type="password", 
              v-model="$v.signUpForm.password.$model", 
              placeholder="Enter password",
              :state="$v.signUpForm.password.$dirty ? !$v.signUpForm.password.$error : null"
              aria-describedby="password-error-message"
            )
            b-form-invalid-feedback#password-error-message This field is required

          .field-wrapper
            b-form-input(type="text", v-model="$v.signUpForm.first_name.$model" placeholder="Enter first name")

          .field-wrapper
            b-form-input(type="text", v-model="$v.signUpForm.last_name.$model" placeholder="Enter last name")

          .auth-action
            button.auth-submit-btn.btn-plant-one-primary.md(:disabled="$v.signUpForm.$invalid", @click="formSubmitted") Sign up
              BtnLoader(v-if="isDataSaving")

          .auth-content-box
            span.content Already have an account?  
            a.btn-plant-one-link.lg(@click="redirectToLogin") Login
  .auth-footer
    .copy-right-text © 2021 Plant One. All Rights Reserved.

</template>

<script>
// libraries
import { required, email } from "vuelidate/lib/validators"

// imports
import APIs from "@/services"

export default {
  name: "SignUp",
  validations: {
    signUpForm: {
      email: { required, email },
      password: { required },
      first_name: {},
      last_name: {},
    }
  },
  data() {
    return {
      signUpForm: {
        email: "",
        password: "",
        first_name: "",
        last_name: "",
        // user_role: "user,admin",
        // username: "Sahil3198",
        // email: "sahil@gmail.com",
        // password: "root",
        // first_name: "Sahil",
        // last_name: "Parekh",
        // dob: "2022-02-15",
        // street: "Adajan",
        // city: "Surat",
        // postal_code: "395009",
        // country: "India",
      },
      isDataSaving: false
    }
  },
  methods: {
    redirectToLogin() {
      const vm = this

      vm.$router.push({ name: "Login" });
    },
    formSubmitted() {
      let vm = this

      if (vm.$v.signUpForm.invalid) {
        return;
      }

      let postDataObj = {
        email: vm.signUpForm.email,
        password: vm.signUpForm.password,
        first_name: vm.signUpForm.first_name,
        last_name: vm.signUpForm.last_name,
      }

      const postData = new FormData();
      postData.append("user", new Blob([JSON.stringify(postDataObj)], { type: "application/json" }));

      vm.isDataSaving = true

      // Call API
      APIs.createUser(postData).then((response)=> {
        vm.$helpers.notify("", "Registered successfully", vm.$helpers.notificationWithMsgType.TOASTER_SUCCESS)

        vm.$router.push({ 
          name: "Login",
          query: {
            email: response.data.email
          }
        });
      }).catch(error => {
        console.error(error)
        vm.$helpers.notify("", "Something went wrong", vm.$helpers.notificationWithMsgType.TOASTER_ERROR)
      }).finally(() => {
        vm.isDataSaving = false
      })
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
