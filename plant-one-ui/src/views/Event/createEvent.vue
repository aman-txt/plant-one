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
        <p>Create an Event</p>
      </div>
    </center>
    <div class="card mb-4" style="max-width: 1000px">
      <center>
        <div class="row no-gutters">
          <div class="card-body">
            <p class="div2">
              Title :
              <input
                id="title"
                v-model="blogDetail.title"
                placeholder="TITLE"
                required
              />
            </p>
            <!--  <b-form-datepicker id="date" v-model="blogDetail.date" class="mb-2"></b-form-datepicker>
                -->
            <p class="div2">
              Event date:
              <input
                type="datetime-local"
                id="date"
                v-model="blogDetail.date"
                name="meeting-time"
                value="2018-06-12T19:30"
              />
            </p>
            <p class="div2">
              Link :
              <input
                id="link"
                v-model="blogDetail.link"
                placeholder="Link"
                required
              />
            </p>
            <p class="div2">
              Maximum Seats :
              <input
                id="avas"
                v-model="blogDetail.maximum_seats"
                placeholder="Maximum Seats"
                required
              />
            </p>
            <p class="div2">
              Mode :
              <input
                id="mode"
                v-model="blogDetail.mode"
                placeholder="mode"
                required
              />
            </p>

            <div class="div2">
              <label for="img">Event Description:</label>
              <!--           <input id="img" type="file" name="img" accept="image/*"/>-->

              <quill-editor
                v-model="blogDetail.details"
                ref="myQuillEditor"
                :options="editorOption"
              ></quill-editor>
              <!--        <textarea id="desc" v-model="blogDetail.details"
                     name="desc" rows="7" cols="40" maxlength="1020" required>You can
                                  Write your description here
                                </textarea>-->
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
import "quill/dist/quill.snow.css";
import { quillEditor } from 'vue-quill-editor'

import APIs from "@/services";
export default {
  name: "Blog",
  components: {
    quillEditor,
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
  data() {
    return {
      name: "blog",
      blogDetail: {
        details: "",
        link: "",
        maximum_seats: 0,
        mode: "",
        title: "",
        date: "",
      },
    };
  },

  methods: {
    async createBlog() {
      const vm = this;
      try {
        //    vm.blogDetail.blogName = document.getElementById("title").value;
        //   vm.blogDetail.article = document.getElementById("desc").value;
        //  vm.blogDetail.user_id=vm.$store.getters.getCurrentUser.user_id;

        const postData = {
          link: vm.blogDetail.link,
          maximum_seats: parseInt(vm.blogDetail.maximum_seats),
          date: Date.parse(vm.blogDetail.date),
          mode: vm.blogDetail.mode,
          details: vm.blogDetail.details,
          title: vm.blogDetail.title,
          user: { user_id: vm.$store.getters.getCurrentUser.user_id },
        };

        await APIs.createEvent(postData);
        vm.$helpers.notify(
          "",
          "Event created successfully",
          vm.$helpers.notificationWithMsgType.TOASTER_SUCCESS
        );
        vm.$router.push({ name: "EventList" });
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
.date {
  margin-bottom: 10%;
}
input {
  // background-color: darkgray ;
}
.card {
  display: flex;
  width: 50%;
  margin-left: 25%;
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

.text_editor {
  margin: 100px;
  padding: 30px;
  background-color: aliceblue;
  width: 500px;
  height: 200px;
  margin: auto;
  border: 3px solid #73ad21;
  color: black;
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


