<template lang="pug">
.form-card
  .form-card-header
    .card-title Create blog

  .card-body
    .field-wrapper.form-flex-row
      .edit.form-flex-col-50
        .form-label Blog title
        b-form-input(
          type="text",
          v-model="$v.blogForm.title.$model",
          placeholder="Enter blog title",
          :state="$v.blogForm.title.$dirty ? !$v.blogForm.title.$error : null",
          aria-describedby="title-error-message"
        )
        b-form-invalid-feedback#title-error-message This field is required

    .field-wrapper.form-flex-row
      .edit.form-flex-col-100.editor-wrapper
        //- .form-label Post type
        quill-editor(
          style="min-height: 250px",
          v-model="$v.blogForm.article.$model",
          ref="quillEditorRef",
          :options="editorOptions"
        )

  .card-footer
    //- button.btn-plant-one-secondary.md(@click="redirectToListScreen") Cancel
    button.btn-plant-one-primary.md(
      :disabled="$v.blogForm.$invalid || isDataSaving",
      @click="createBlog()"
    )
      span Save
      BtnLoader(v-if="isDataSaving")
</template>

<script>
import "quill/dist/quill.snow.css";
import APIs from "@/services";
import { required } from "vuelidate/lib/validators";

import { quillEditor } from "vue-quill-editor";

export default {
  name: "AddBlog",
  components: {
    quillEditor,
  },
  validations: {
    blogForm: {
      title: { required },
      article: {},
    },
  },
  data() {
    return {
      isDataSaving: false,
      blogForm: {
        title: "",
        article: "",
      },

      editorOptions: {},
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

    vm.editorOptions = {
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

      vm.$v.blogForm.$touch();
      if (vm.$v.blogForm.$invalid) {
        return;
      }

      try {
        vm.isDataSaving = true;
        const postData = {
          article: vm.blogForm.article,
          blogName: vm.blogForm.blogName,
          user: {
            user_id: vm.$store.getters.getCurrentUser.user_id,
          },
        };

        await APIs.createBlog(postData);
        vm.$helpers.notify(
          "",
          "Blog created successfully",
          vm.$helpers.notificationWithMsgType.TOASTER_SUCCESS
        );
      } catch (error) {
        console.error(error);
        vm.$helpers.notify(
          "",
          "Something went wrong",
          vm.$helpers.notificationWithMsgType.TOASTER_ERROR
        );
      } finally {
        vm.isDataSaving = false;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.quill-editor .ql-toolbar {
  .ql-link,
  .ql-image,
  .ql-video {
    display: none;
  }
}
.editor-wrapper {
  min-height: 250px;

  .ql-container,
  .ql-editor {
    min-height: inherit;
  }
}
</style>