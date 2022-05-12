<template lang="pug">
  
</template>

<script>
import APIs from "@/services";

export default {
  name: "VerifyChangePassword",
  created() {
    const vm = this

    vm.verifyChangePasswordToken()
  },
  methods: {
    async verifyChangePasswordToken() {
      const vm = this
      
      const token = vm.$route.params.token ? vm.$route.params.token : ""

      try {
        const queryParams = {
          token
        }
        await APIs.verifyChangePasswordToken(queryParams)

        vm.$router.push({
          name: "ChangePassword",
          params: {
            token: token
          }
        })

      } catch (error) {
        console.error(error)
        vm.$helpers.notify("", "Something went wrong", vm.$helpers.notificationWithMsgType.TOASTER_ERROR)
      }
    }
  }
}
</script>

<style lang="scss" scoped>

</style>