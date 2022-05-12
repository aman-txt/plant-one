<template>
  <div class="bodyy">
    <div class="everything">
      <center>
        <p class="heading">Event Details</p>
        <div class="card" style="width: 40rem">
          <div class="card-body">
            <h5 class="card-title">{{ event.title }}</h5>
            <div class="desc" v-html="event.details"></div>
            <p class="card-text">
              Available Seats: {{ event.available_seats }}
            </p>
            <p class="card-text">Maximum Seats{{ event.maximum_seats }}</p>
            <p class="card-text">Event date:{{ event.date }}</p>
            <p class="card-text">Link:{{ event.link }}</p>
            <p class="card-text">Mode:{{ event.mode }}</p>

            <button @click="success(user_Id)" class="btn btn-success">
              Book
            </button>
          </div>
        </div>
      </center>
    </div>
  </div>
</template>
<script>
import APIs from "@/services";

export default {
  name: "feed",
  data() {
    return {
      eventId: 0,
      user_Id: 0,
      event: [],
    };
  },
  created() {
    const vm = this;
    vm.eventId = vm.$route.params.eventUUID;
    vm.user_Id = vm.$store.getters.getCurrentUser.user_id;
    this.getEvents();
  },
  methods: {
    async success(user_Id) {
      const vm = this;
      try {
        const postdata = {
          event: { eventUUID: vm.eventId },
          user: { user_id: vm.user_Id },
        };

        const regNo = await APIs.registerEvent(postdata);
        vm.$helpers.notify(
          "",
          "Registered successfully",
          vm.$helpers.notificationWithMsgType.TOASTER_SUCCESS
        );
        vm.$router.push({
          name: "EventRegister",
          params: {
            user_Id: user_Id,
            lnam: vm.event.user.last_name,
            fnam: vm.event.user.first_name,
            date: vm.event.date,
            eventId: vm.eventId,
            title: vm.event.title,
          },
        });
      } catch (error) {
        vm.$helpers.notify(
          "",
          "Something went wrong",
          vm.$helpers.notificationWithMsgType.TOASTER_ERROR
        );
      }
    },
    async getEvents() {
      const vm = this;
      try {
        const getEventsResponse = await APIs.getEvent(vm.eventId);
        vm.event = getEventsResponse.data;
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
  font-family: "Montserrat", sans-serif;
  color: black;
  padding-bottom: 10px;
}
</style>