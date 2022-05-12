
<template>
  <div class="vid">
    <div class="vidbg">
      <link rel="preconnect" href="https://fonts.googleapis.com" />
      <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
      <link
        href="https://fonts.googleapis.com/css2?family=Montserrat:wght@500&display=swap"
        rel="stylesheet"
      />
      <img
        class="video"
        src="https://www.teahub.io/photos/full/90-902100_desktop-wallpaper-plants.jpg"
      />
      <div class="btn" style="position: fixed; top: 80px; right: 70px">
        <button type="button" class="btn btn-success">
          <router-link class="nav-link" :to="{ name: 'EditBlog' }"
            >Create Blog</router-link
          >
        </button>
      </div>
      <div class="overlay">
        <div class="p">
          <p>Blogs</p>
        </div>
      </div>
    </div>
    <div class="main">
      <div class="feed">
        <div
          v-for="post in posts"
          :key="post.id"
          class="card mb-4"
          style="max-width: 1000px"
        >
          <div class="row no-gutters">
            <div class="col-md-4">
              <img
                src="https://www.thegreatcoursesdaily.com/wp-content/uploads/2017/12/can-plants-think_FeaturedThumb.jpg"
                class="card-img"
                alt="..."
              />
            </div>
            <div class="col-md-8">
              <div class="card-body">
                <h5 class="card-title">{{ post.blogName }}</h5>
                <p class="card-text-left" v-html="post.article"></p>
              </div>
            </div>
            <div class="card-footer">
              <link
                rel="stylesheet"
                href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
              />

              <button
                id="b1"
                @click="upVote(post.blogUUID)"
                class="fa fa-thumbs-up"
              >
                <p class="card-text">{{ post.voteCount }}</p>
              </button>
              <button
                id="b2"
                style="color: red"
                @click="deleteBlog(post.blogUUID)"
                class="fa fa-trash"
              ></button>
              <button
                type="button"
                class="btn btn-success"
                @click="redirectToDetails(post.blogUUID)"
              >
                Details
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import APIs from "@/services";
export default {
  name: "feed",

  data() {
    return {
      posts: [],
    };
  },
  created() {
    const vm = this;
    vm.getBlogs();
  },
  methods: {
    async upVote(blogUUID) {
      const vm = this;
      try {
        await APIs.upvoteBlog(
          blogUUID,
          vm.$store.getters.getCurrentUser.user_id
        );

        const i = vm.posts.findIndex(b => b.blogUUID === blogUUID)
        if (i > -1) {
          const blog = vm.$_.cloneDeep(vm.posts[i])
          const previousLikeState = blog.likedByUser
          const previousVoteCount = blog.voteCount

          if (previousLikeState) {
            blog.likedByUser = false
            blog.voteCount = previousVoteCount - 1
          } else {
            blog.likedByUser = true
            blog.voteCount = previousVoteCount + 1
          }

          vm.$set(vm.posts, i, blog)
        }

      } catch (error) {
        vm.$helpers.notify(
          "",
          "Something went wrong",
          vm.$helpers.notificationWithMsgType.TOASTER_ERROR
        );
      }
    },
    async deleteBlog(blogUUID) {
      const vm = this;
      try {
        await APIs.deleteBlog(blogUUID);

        const i = vm.posts.findIndex(b => b.blogUUID === blogUUID)
        if (i > -1) {
          vm.posts.splice(i, 1)
        }

        vm.$helpers.notify("", "Blog deleted successfully", vm.$helpers.notificationWithMsgType.TOASTER_SUCCESS)
      } catch (error) {
        vm.$helpers.notify(
          "",
          "Something went wrong",
          vm.$helpers.notificationWithMsgType.TOASTER_ERROR
        );
      }
    },
    redirectToDetails(blogUUID) {
      const vm = this;
      vm.$router.push({ name: "BlogDetail", params: { blogUUID: blogUUID } });
    },

    async getBlogs() {
      const vm = this;
      try {
        const getBlogsResponse = await APIs.getBlogs(
          vm.$store.getters.getCurrentUser.user_id
        );
        vm.posts = getBlogsResponse.data;
      } catch (error) {
        vm.$helpers.notify(
          "",
          "Something went wrong",
          vm.$helpers.notificationWithMsgType.TOASTER_ERROR
        );
      }
    },
  },
};
</script>


<style scoped>
.button{
  display: inline;
}
.card-footer{
  display: inline;

  flex-shrink: 0;
  width: 100%;
  max-width: 100%;
  padding-right: calc(var(--bs-gutter-x) * 0.5);
  padding-left: calc(var(--bs-gutter-x) * 0.5);
  margin-top: var(--bs-gutter-y);
}



.fa:hover {
  color: #002ead;
}

.fa {
  margin-left: 35px;
  margin-right: 38%;
  border: none;
  color: black;
  display: inline;
}
.nav-link {
  font-family: "Montserrat", sans-serif;

  color: white;
}
.btn {
  z-index: 100;
}
.card-title {
  font-family: "Montserrat", sans-serif;
  font-weight: bold;
}
.vid {
  min-width: 100%;
  min-height: 100%;
  background: rgb(33, 37, 41);
}
.card-text {
  align-content: left;
}

.vidbg {
  width: 100%;
  background: black;
  overflow: hidden;
}
.overlay {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1;
}
.main {
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.p {
  font-family: "Montserrat", sans-serif;
  font-size: 72px;
  font-weight: 900;
  color: rgb(255, 255, 255);
}

.video {
  width: 100%;
  height: 100vh;
  opacity: 0.4;
  object-fit: cover;
}

.main .feed {
  z-index: 1;
  margin: auto;
  width: 70%;

  padding: 10px;
}
.card-img {
  height: auto;
  width: auto;
  max-width: 300px;
  max-height: 300px;
}
</style>