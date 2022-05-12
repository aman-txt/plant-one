<template>
  <div class="bodyy">
    <div class="everything">
      <center>
        <p class="heading">Blog Details</p>
        <div class="card" style="width: 40rem">
          <div class="card-body">
            <h5 class="card-title">Title:{{ details.blogName }}</h5>
            <div class="desc" v-html="details.article"></div>
            <p class="card-text">Date:{{ details.createdDate }}</p>
            <p class="card-text">
              Created by:<strong>
                {{ details.user.first_name }}
                {{ details.user.last_name }}</strong
              >
            </p>
          </div>
        </div>
      </center>
    </div>
  </div>
</template>
<script>
import APIs from "@/services";
export default {
  //name:"BlogDetails",
  data() {
    return {
      blogUUID: 0,
      details: [],
    };
  },
  created() {
    const vm = this;
    vm.blogUUID = vm.$route.params.blogUUID;
    this.getDetails();
  },
  methods: {
    async getDetails() {
      const vm = this;
      try {
        const getBlogResponse = await APIs.getBlog(vm.blogUUID);
        vm.details = getBlogResponse.data;
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
.desc {
  border-radius: 25px;
  background: gainsboro;
  padding: 20px;

  margin-bottom: 10px;
}
.bodyy {
  width: 100%;
  height: 100%;
  background-color: rgb(33, 37, 41);
}
.everything {
  padding-top: 60px;
}
.heading {
  font-family: "Montserrat", sans-serif;
  font-size: 32px;
  font-weight: 400;
  color: rgb(255, 255, 255);
  padding-bottom: 30px;
}
.card-text {
  margin-bottom: 10px;
}
</style>