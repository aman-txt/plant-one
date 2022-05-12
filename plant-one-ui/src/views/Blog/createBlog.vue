<template >
  <div class="div1">
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Montserrat:wght@500&display=swap"
      rel="stylesheet"
    />

    <center>
      <div class="p">
        <p>Create a Blog</p>
      </div>
    </center>
    <div class="card mb-4">
      <center>
        <div class="row no-gutters">
          <div class="card-body">
            <p class="div2">
              Title :
              <input
                id="title"
                v-model="blogDetail.blogName"
                placeholder="TITLE"
                required
              />
            </p>
            <div class="div2">
              <label for="img">Blog Description:</label>

              <div class="field-wrapper form-flex-row">
                <div class="edit form-flex-col-100 editor-wrapper">
                  <quill-editor
                    v-model="blogDetail.article"
                    ref="myQuillEditor"
                    :options="editorOption"
                  ></quill-editor>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="card-footer">
          <button class="btn-plant-one-primary md" @click="createBlog()">
            <span>Create</span>
          </button>
        </div>
      </center>
    </div>
  </div>
</template>
<script>

import 'quill/dist/quill.snow.css'
import { quillEditor } from 'vue-quill-editor'

import APIs from "@/services";
export default {
  name: "Blog",
  components: {
    quillEditor
  },

  data() {
    return {
      editorOption: {},

      name: "blog",
      blogDetail: {
        article: "",
        blogName: "",
      },
    };
  },

  created() {
    const vm = this;

    const toolbarOptions = [
      ["bold", "italic", "underline", "strike"], // toggled buttons
      ["blockquote", "code-block"],

      [{ header: 1 }, { header: 2 }], // custom button values
      [{ list: "ordered" }, { list: "bullet" }],
      [{ script: "sub" }, { script: "super" }], // superscript/subscript
      [{ indent: "-1" }, { indent: "+1" }], // outdent/indent
      [{ direction: "rtl" }], // text direction

      [{ size: ["small", false, "large", "huge"] }], // custom dropdown
      [{ header: [1, 2, 3, 4, 5, 6, false] }],

      [{ color: [] }, { background: [] }], // dropdown with defaults from theme
      [{ font: [] }],
      [{ align: [] }],

      ["clean"], // remove formatting button
    ];

    vm.editorOption = {
      debug: "false",
      modules: {
        toolbar: toolbarOptions,
      },
      placeholder: "Type your post...",
      readOnly: true,
      theme: "snow",
    };
  },

  methods: {
    async createBlog() {
      const vm = this;
      try {
        const postData = {
          article: vm.blogDetail.article,
          blogName: vm.blogDetail.blogName,
          user: { user_id: vm.$store.getters.getCurrentUser.user_id },
        };
        await APIs.createBlog(postData);
        vm.$helpers.notify(
          "",
          "Blog created successfully",
          vm.$helpers.notificationWithMsgType.TOASTER_SUCCESS
        );
        vm.$router.push({ name: "BlogList" });
      } catch (error) {
        console.error(error);
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

<style lang="scss" scoped>
.card {
  display: flex;
  width: 50%;
  margin-left: 27%;
  padding: 50px;
  background: lightgrey;
  box-sizing: border-box;
}

.p {
  z-index: 10;
  // font-family: "Montserrat", sans-serif;
  @include is-size-h1-b;
  font-size: 52px !important;
  // font-weight: 900;
  color: white;
  padding-bottom: 20px;
}

.div2 {
  font-family: "Montserrat", sans-serif;
  color: black;

  padding-bottom: 20px;
}
.nav-link {
  font-family: "Montserrat", sans-serif;
  color: white;
}
#bg {
  position: fixed;
  top: 0;
  left: 0;
  z-index: -1;
  /* Preserve aspet ratio */
  min-width: 100%;
  min-height: 100%;
}
.card {
  margin-left: 23%;
  background-color: white;
}

.div1 {
  position: relative;
  z-index: 1;
  background-color: rgb(33, 37, 41);
  margin: auto;
  width: 100%;
  padding: 50px;
}

button {
  background-color: #4caf50; /* Green */
  border-radius: 12px;
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
}
</style>


