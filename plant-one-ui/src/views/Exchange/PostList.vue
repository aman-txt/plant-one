<template lang="pug">
.animated.fadeIn
  .content-wrapper
    .page-header-wrapper
      .page-header
        .title Exchange
        .action-container
            button.btn-plant-one-secondary.md(@click="redirectToCart") Cart
            button.btn-plant-one-primary.md(@click="redirectToCreatePost") Create post

    .filter-container
      .filter-item
        b-form-select(
          v-model="selectedPlantType",
          :options="plantTypeOptions",
        )

      .filter-item
        b-form-select(
          v-model="selectedPostType",
          :options="postTypeOptions",
        )

      .clear-filter(v-if="isAnyFilterSelected")
        .btn-plant-one-link(@click="resetAllFilter") Clear filters

      .action-container
        .action-item
          b-form-input(
            type="search",
            v-model="searchTerm",
            placeholder="Search plant",
          )

    .post-list-wrapper
      template(v-if="postsToDisplay.length > 0" v-for="post in postsToDisplay")
        PostCard(
          :key="post.postUUID",
          :postDetails="post",
          :showAddToCard="userProfile.user_id !== post.seller.user_id",
          @addedToCart="handelAddedToCart",
          @updatePostData="handleUpdatePostData"
        )


</template>

<script>
import APIs from "@/services";
import PostCard from "@/views/Exchange/PostCard"

export default {
  name: "PostList",
  components: {
    PostCard
  },
  data() {
    return {
      posts: [],
      userProfile: {},

      plantTypeOptions: [],
      postTypeOptions: [],

      selectedPlantType: null,
      selectedPostType: null,
      searchTerm: "",
    }
  },
  computed: {
    postsToDisplay() {
      const vm = this

      let postsToDisplay = vm.$_.cloneDeep(vm.posts);

      if (vm.selectedPlantType != null) {
        postsToDisplay = postsToDisplay.filter(p => p.plantType.plantTypeId === vm.selectedPlantType)
      }

      if (vm.selectedPostType != null) {
        postsToDisplay = postsToDisplay.filter(p => p.postType.postTypeId === vm.selectedPostType)
      }

      if (vm.searchTerm !== "") {
        postsToDisplay = postsToDisplay.filter(p => p.title.includes(vm.searchTerm))
      }

      return postsToDisplay
    },

    isAnyFilterSelected() {
      const vm = this

      return (
        vm.selectedPlantType != null ||
        vm.selectedPostType != null ||
        vm.searchTerm !== ""
      )
    }
  },
  created() {
    const vm = this

    vm.getInitialData();

    vm.userProfile = vm.$store.getters.getCurrentUser;
    vm.getPosts();
  },

  methods: {
    async getInitialData() {
      const vm = this

      try {
        const plantTypesResponse = await APIs.getPlantTypes()
        const postTypesResponse = await APIs.getPostTypes()

        vm.plantTypeOptions = plantTypesResponse.data.map(o => { return { value: o.plantTypeId, text: o.plantType } })
        vm.postTypeOptions = postTypesResponse.data.map(o => { return { value: o.postTypeId, text: o.postType } })
        vm.plantTypeOptions.unshift({ value: null, text: "Filter by plant type" })
        vm.postTypeOptions.unshift({ value: null, text: "Filter by post type" })
      } catch (error) {
        console.error(error);
        vm.$helpers.notify("", "Something went wrong", vm.$helpers.notificationWithMsgType.TOASTER_ERROR)
      }
    },

    async getPosts() {
      const vm = this

      try {
        const postsResponse = await APIs.getPosts(vm.userProfile.user_id);
        vm.posts = postsResponse.data;
      } catch (error) {
        console.error(error);
        vm.$helpers.notify("", "Something went wrong", vm.$helpers.notificationWithMsgType.TOASTER_ERROR)
      }
    },

    resetAllFilter() {
      const vm = this

      vm.selectedPlantType = null
      vm.selectedPostType = null
      vm.searchTerm = ""
    },

    handelAddedToCart(postId) {
      const vm = this

      const i = vm.posts.findIndex(p => p.postUUID === postId);
      if (i > -1) {
        vm.posts.splice(i, 1);
      }
    },

    handleUpdatePostData(updatedData) {
      const vm = this

      const i = vm.posts.findIndex(p => p.postUUID === updatedData.postUUID);
      if (i > -1) {
        vm.$set(vm.posts, i, vm.$_.cloneDeep(updatedData))
      }
    },

    redirectToCart() {
      const vm = this

      vm.$router.push({
        name: "Cart"
      })
    },

    redirectToCreatePost() {
      const vm = this

      vm.$router.push({
        name: "CreatePost"
      })
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

.post-list-wrapper {
  @include flexbox();
  @include flex-direction(column);
  @include align-items(center);

  overflow: auto;

  height: calc(100vh - 265px);
  width: 100%;

  margin: 20px;
  padding: 0 10px 20px 0;

  @include webkit-scroller;
}
</style>