<template lang="pug">
.animated.fadeIn
  .content-wrapper
    .breadcrumb-container
        router-link.btn-plant-one-link(:to="{name: 'PostList'}") Exchange
        span.crumb-txt /

    .page-header-wrapper
      .page-header
        .title Cart
        .action-container
            button.btn-plant-one-primary.md(@click="checkoutCart" :disabled="cartItems.length == 0") Checkout

    .page-body
      table.table.table-bordered.table-striped
        thead.thead-dark
          tr
            th Plant name
            th Plant type
            th Post type
            th Price
            th Seller
            th 

        tbody
          tr(v-for="item in cartItems" :key="item.cartEntryUUID")
            td {{ item.post.title }}
            td {{ item.post.plantType.plantType }}
            td {{ item.post.postType.postType }}
            td {{ item.post.price }}
            td {{ item.post.seller.first_name + " " + item.post.seller.last_name }}
            td
              i.fa-solid.fa-trash-can.delete-icon.pointer(@click="removeFromCart(item.cartEntryUUID)")

</template>

<script>
import APIs from "@/services";

export default {
  name: "Cart",
  data() {
    return {
      cartItems: [],
    }
  },
  created() {
    const vm = this

    vm.getCartItems()
  },
  methods:{
    async getCartItems() {
      const vm = this

      try {
        const cartItemsResponse = await APIs.getCartItems(vm.$store.getters.getCurrentUser.user_id)
        vm.cartItems = cartItemsResponse.data
      } catch (error) {
        console.error(error);
        vm.$helpers.notify("", "Something went wrong", vm.$helpers.notificationWithMsgType.TOASTER_ERROR)
      }
    },

    async removeFromCart(cartItemId) {
      const vm = this

      try {
        await APIs.removeFromCart(cartItemId)
        
        const i = vm.cartItems.findIndex(o => o.cartEntryUUID === cartItemId);
        if (i > -1) {
          vm.cartItems.splice(i, 1);
        }

        if (vm.cartItems.length == 0) {
          vm.$router.push({ name: "PostList" })
        }

        vm.$helpers.notify("", "Item removed from cart successfully", vm.$helpers.notificationWithMsgType.TOASTER_SUCCESS)
      } catch (error) {
        console.error(error);
        vm.$helpers.notify("", "Something went wrong", vm.$helpers.notificationWithMsgType.TOASTER_ERROR)
      }
    },

    async checkoutCart() {
      const vm = this

      if (vm.cartItems.length == 0) {
        return
      }

      try {
        await APIs.checkoutCart(vm.$store.getters.getCurrentUser.user_id)
        
        vm.cartItems = []

        vm.$helpers.notify("", "Cart checked out successfully", vm.$helpers.notificationWithMsgType.TOASTER_SUCCESS)

        vm.$router.push({ name: "PostList" })
      } catch (error) {
        console.error(error);
        vm.$helpers.notify("", "Something went wrong", vm.$helpers.notificationWithMsgType.TOASTER_ERROR)
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.animated {
  background-image: url(https://images.unsplash.com/photo-1483794344563-d27a8d18014e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80);
  background-repeat: no-repeat;
  background-size: cover;
}

.content-wrapper {
  padding: 10px 30px;
}

.page-body {
  height: calc(100vh - 154px)
}

.delete-icon {
  color: $brand-e;
}

</style>