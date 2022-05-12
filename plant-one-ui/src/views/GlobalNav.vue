<template lang="pug">
  .global-nav-wrapper
    nav.navbar.navbar-expand-lg.navbar-dark.bg-dark
      .container-fluid
        a.navbar-brand PlantOne
        
        button.navbar-toggler(
          type="button",
          data-toggle="collapse",
          data-target="#navbarSupportedContent",
          aria-controls="navbarSupportedContent",
          aria-expanded="false",
          aria-label="Toggle navigation"
        )
          span.navbar-toggler-icon

        .collapse.navbar-collapse#navbarSupportedContent
          .navbar-nav
            .nav-item
              router-link.nav-link(:to="{ name: 'EventList' }") Events
            .nav-item
              router-link.nav-link(:to="{ name: 'BlogList' }") Knowledge
            .nav-item
              router-link.nav-link(:to="{ name: 'PostList' }") Exchange

          .navbar-nav.ml-auto
            .nav-item
              router-link.nav-link(:to="{ name: 'Profile' }") Profile
            .nav-item
              a.nav-link.pointer(@click="handleSignOut()") Sign out

</template>

<script>
export default {
  name: "GlobalNav",
  methods: {
    handleSignOut() {
      const vm = this

      vm.$cookies.remove("authInfo")
      vm.$router.push({ name: "Login" })
    }
  }
}
</script>

<style lang="scss" scoped>
.global-nav-wrapper{
  z-index: 999;
  position: fixed;
  width: 100%;
}
</style>
