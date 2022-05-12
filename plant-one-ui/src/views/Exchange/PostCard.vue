<template lang="pug">
  .form-card.post-card-wrapper
    .form-card-header
      .card-title {{ postDetails.title || "-" }}
      .actions
        i.fa-solid.fa-thumbs-up.like-icon.pointer(
          v-if="isClickable",
          :class="{ liked: postDetails.likedByUser }",
          @click.stop="likePost"
        )
        span.like-count {{ postDetails.likesCount }} like(s)

    .card-body.container
      .row(v-if="postDetails.base64Image")
        .col-12
          .image-container
            img(:src="postDetails.base64Image")

      .row
        .col-3.form-label Plant type
        .col-6.form-value {{ postDetails.plantType.plantType || "-" }}

      .row
        .col-3.form-label Post type
        .col-6.form-value {{ postDetails.postType.postType || "-" }}

      .row(v-if="postDetails.postType.postType !== 'Adoption'")
        .col-3.form-label Price
        .col-6.form-value {{ postDetails.price || "-" }}

      .row
        .col-3.form-label Seller
        .col-6.form-value {{ postDetails.seller.first_name + ' ' + postDetails.seller.last_name || "-" }}
      
      .w-100.d-flex
        div.ml-auto
          button.btn-plant-one-secondary.sm(v-if="showAddToCard", @click.stop="addToCart") Add to Cart
          button.btn-plant-one-secondary.sm.ml-3(v-if="isClickable", @click="redirectToPostDetails(postDetails.postUUID)") Details

</template>

<script>
import APIs from "@/services";

export default {
  name: "PostCard",
  props: {
    showAddToCard: {
      type: Boolean,
      required: false,
      default: true
    },
    isClickable: {
      type: Boolean,
      required: false,
      default: true
    },
    postDetails: {
      type: Object,
      required: true,
      default: () => {}
    }
  },
  methods: {
    redirectToPostDetails(postId) {
      const vm = this

      vm.$router.push({
        name: "PostDetails",
        params: {
          postId: postId
        }
      })
    },

    async likePost() {
      const vm = this

      try {
        await APIs.likePost(vm.postDetails.postUUID, vm.$store.getters.getCurrentUser.user_id)

        const post = vm.$_.cloneDeep(vm.postDetails)

        const previousLikeState = post.likedByUser
        const previousLikesCount = post.likesCount

        if (previousLikeState) {
          post.likedByUser = false
          post.likesCount = previousLikesCount - 1
        } else {
          post.likedByUser = true
          post.likesCount = previousLikesCount + 1
        }
        
        vm.$emit("updatePostData", post);
      } catch (error) {
        console.error(error);
        vm.$helpers.notify("", "Something went wrong", vm.$helpers.notificationWithMsgType.TOASTER_ERROR)
      }
    },

    async addToCart() {
      const vm = this

      const userProfile = vm.$store.getters.getCurrentUser;

      try {
        await APIs.addToCart(userProfile.user_id, vm.postDetails.postUUID)
        vm.$helpers.notify("", "Post added to cart successfully", vm.$helpers.notificationWithMsgType.TOASTER_SUCCESS)

        // Emit an even to notify parent that post has been added to cart
        vm.$emit("addedToCart", vm.postDetails.postUUID)
      } catch (error) {
        console.error(error);
        vm.$helpers.notify("", "Something went wrong", vm.$helpers.notificationWithMsgType.TOASTER_ERROR)
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.post-card-wrapper {
  @include shadow-style-5;
  width: 600px;

  margin-left: auto;
  margin-right: auto;
}

.form-card-header {
  @include flexbox();
  @include justify-content(space-between);
}

.card-body {
  padding: 0 40px 40px 40px;

  .row {
    margin-bottom: 10px;
  }
}

.image-container {
  @include flexbox;
  @include flex-direction(column);
  @include align-items(center);

  max-height: 300px;
  width: 100%;

  img {
    max-height: 300px;
    width: 100%;
  }
}

.like-count {
  @include is-size-h3-r;
  color: $brand-n2;
}

.like-icon {
  height: 30px;
  width: 30px;
  font-size: 24px;

  &.liked {
    color: $brand-p1;
  }
}
</style>