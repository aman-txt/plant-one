
<template>
  <div class="vid">
    <div class="vidbg">
      <link rel="preconnect" href="https://fonts.googleapis.com" />
      <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
      <link
        href="https://fonts.googleapis.com/css2?family=Montserrat:wght@500&display=swap"
        rel="stylesheet"
      />
      <img class="video" src="https://wallpaperaccess.com/full/6862458.jpg" />
      <div class="btn" style="position: fixed; top: 80px; right: 70px">
        <button type="button" class="btn btn-success">
          <router-link class="nav-link" :to="{ name: 'EditEvent' }"
            >Create Event</router-link
          >
        </button>
      </div>
      <div class="overlay">
        <div class="p">
          <p>Events</p>
        </div>
      </div>
    </div>
    <div class="main">
      <div class="feed">
        <div
          v-for="post in events"
          :key="post.id"
          class="card mb-4"
          style="max-width: 500px"
        >
          <div class="row no-gutters">
            <div class="col">
              <div class="card-body">
                <h5 class="card-title">{{ post.title }}</h5>
                <p class="card-text-left" v-html="post.details">
                  {{ post.details }}
                </p>

                <p class="card-text">
                  <small class="text-muted"
                    >Available seats:{{ post.available_seats }}</small
                  >
                </p>

                <p class="card-text">
                  <small class="text-muted"
                    >Maximum seats:{{ post.maximum_seats }}</small
                  >
                </p>
              </div>
            </div>
            <div class="card-footer">
              <button
                style="color: red"
                @click="deleteEvent(post.eventUUID)"
                class="fa fa-trash"
              ></button>

              <button
                type="button"
                class="btn btn-success"
                @click="redirectToDetails(post.eventUUID)"
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
      events: [],
    };
  },
  created() {
    const vm = this;
    vm.getEvents();
  },
  methods: {
    async deleteEvent(eventUUID) {
      const vm = this;
      try {
        await APIs.deleteEvent(eventUUID);

        const i = vm.events.findIndex(event => event.eventUUID === eventUUID)
        if (i > -1) {
          vm.events.splice(i, 1)
        }

        vm.$helpers.notify("", "Event deleted successfully", vm.$helpers.notificationWithMsgType.TOASTER_SUCCESS)
      } catch (error) {
        vm.$helpers.notify(
          "",
          "Something went wrong",
          vm.$helpers.notificationWithMsgType.TOASTER_ERROR
        );
      }
    },

    redirectToDetails(eventUUID) {
      const vm = this;
      vm.$router.push({
        name: "EventDetail",
        params: { eventUUID: eventUUID },
      });
    },

    async getEvents() {
      const vm = this;
      try {
        const getEventsResponse = await APIs.getEvents();
        vm.events = getEventsResponse.data;
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
.fa {
  margin-right: 50%;
  margin-bottom:20px;
  border: none;
}
.btn {
  z-index: 100;
}
.btn btn-success {
  font-size: 14px;
}
.nav-link {
  font-family: "Montserrat", sans-serif;
  color: white;
}
.card {
  margin-top: 3%;
  margin-left: 30%;
  text-align: center;
}
.main {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.vid{
  min-width: 100%;
  min-height: 100%;
  background: rgb(33,37,41);
}
.card-title {
  font-family: "Montserrat", sans-serif;
  font-weight: bold;
}
.card-text {
  align-content: left;
}

.vidbg {
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
  align-items: center;
  z-index: 1;
  background: rgb(33, 37, 41);
  padding: 10px;
}
.card-img {
  height: auto;
  width: auto;
  max-width: 300px;
  max-height: 300px;
}
</style>