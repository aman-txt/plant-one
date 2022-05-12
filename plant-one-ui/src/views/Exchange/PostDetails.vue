<template lang="pug">
.animated.fadeIn
  .content-wrapper
    .breadcrumb-container
        router-link.btn-plant-one-link(:to="{name: 'PostList'}") Exchange
        span.crumb-txt /

    .page-header-wrapper
      .page-header
        .title Post details

    .page-body
      PostCard(
        v-if="!$_.isEmpty(postDetails)",
        :showAddToCard="false",
        :isClickable="false",
        :postDetails="postDetails")

      .comments-card-wrapper
        .form-card
          .form-card-header
            .card-title Comments

          .card-body.container
            .comment-list-wrapper
              .add-comment-wrapper
                b-form-textarea(
                  id="textarea"
                  v-model.trim="newComment"
                  placeholder="Add comment..."
                  rows="3"
                  max-rows="6"
                )

                button.btn-plant-one-primary.md(
                  :disabled="newComment.length <= 0 || isDataSaving",
                  @click="addComment()"
                )
                  span Add
                  BtnLoader(v-if="isDataSaving")

              .comment-box(v-for="commentObj in postDetails.comment" :key="commentObj.commentUUID")
                .comment-by
                  span {{ (commentObj.user.first_name + ' ' + commentObj.user.last_name).trim() }}
                  a.btn-plant-one-link.sm.delete-btn(
                    v-if="commentObj.user.user_id === loggedInUser.user_id"
                    @click="deleteComment(commentObj.commentUUID)"
                  ) Delete

                .comment-text
                  span {{ commentObj.comment }}

</template>

<script>
import APIs from "@/services"
import PostCard from "@/views/Exchange/PostCard"

export default {
  name: "PostDetails",
  components: {
    PostCard
  },
  data() {
    return {
      postDetails: {},
      newComment: "",
      isDataSaving: false,
    }
  },
  created() {
    const vm = this

    vm.getPostDetails()
  },
  computed: {
    loggedInUser() {
      const vm = this
      return vm.$_.cloneDeep(vm.$store.getters.getCurrentUser)
    }
  },
  methods: {
    async getPostDetails() {
      const vm = this

      const postId = vm.$route.params.postId

      try {
        const postDetailsResponse = await APIs.getPost(postId)
        vm.postDetails = postDetailsResponse.data
      } catch (error) {
        console.error(error);
        vm.$helpers.notify("", "Something went wrong", vm.$helpers.notificationWithMsgType.TOASTER_ERROR)
      }

    },

    async addComment() {
      const vm = this

      const postData = {
        comment: vm.newComment,
        user: { user_id: vm.$store.getters.getCurrentUser.user_id },
      }

      try {
        const addCommentResponse = await APIs.addComment(vm.$route.params.postId, postData)

        // Update postDetails using the updated data of post from API response
        vm.postDetails = addCommentResponse.data

        // reset the variable
        vm.newComment = ""

        vm.$helpers.notify("", "Comment added successfully", vm.$helpers.notificationWithMsgType.TOASTER_SUCCESS)
      } catch (error) {
        console.error(error);
        vm.$helpers.notify("", "Something went wrong", vm.$helpers.notificationWithMsgType.TOASTER_ERROR)
      }
    },

    async deleteComment(commentId) {
      const vm = this

      try {
        await APIs.deleteComment(commentId)

        const i = vm.postDetails.comment.findIndex(c => c.commentUUID === commentId)
        if (i > -1) {
          vm.postDetails.comment.splice(i, 1)
        }

        vm.$helpers.notify("", "Comment deleted successfully", vm.$helpers.notificationWithMsgType.TOASTER_SUCCESS)
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

textarea {
  @include webkit-scroller;
}

.content-wrapper {
  padding: 10px 30px;
  min-height: calc(100vh - 50px);
}

.comments-card-wrapper {
  width: 600px;

  margin: 0 auto;
}

.form-card-header {
  @include flexbox();
  @include justify-content(space-between);
}

.like-icon {
  font-size: 24px;
  color: $brand-p1;
}

.comment-box ~ .comment-box {
  margin-top: 5px;
}

.comment-box {

  .comment-by {
    @include flexbox;
    @include align-items(center);

    @include is-size-h3-sb;
    color: $brand-n2;

    .delete-btn {
      margin-left: 10px;
    }
  }

  .comment-text {
    @include is-size-h4-r;
    color: $brand-n3;
  }
}

.add-comment-wrapper {
  @include flexbox;
  @include align-items(flex-end);

  margin-bottom: 15px;

  button {
    margin-left: 10px;
  }
}
</style>