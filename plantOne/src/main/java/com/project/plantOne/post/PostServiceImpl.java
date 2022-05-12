package com.project.plantOne.post;



import com.project.plantOne.like.LikesRepository;
import com.project.plantOne.user.UserController;
import com.project.plantOne.user.UserRepository;
import com.project.plantOne.util.CustomModelMapper;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import java.io.IOException;
import java.time.Instant;
import java.util.*;

import static com.project.plantOne.constants.Constants.*;


@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PlantTypeRepository plantTypeRepository;

    @Autowired
    private PostTypeRepository postTypeRepository;

    @Autowired
    private LikesRepository likesRepository;

    UUID uuid;


    public List<PostResponse> getPostList(UUID userUUID) {

        //Get only active post from all the other sellers
        List<Post> postList = postRepository.getActivePosts(userUUID);
        List<PostResponse> postResponsesList = new ArrayList<>();
        for (Post post:postList) {
            PostResponse postResponse = new PostResponse();

            postResponse = CustomModelMapper.postResponseModelMapper(post);
            if(post.getPlantImage()!=null){
                String encodeToString = "data:image/"+post.getFileExtension()+";base64," + Base64.getEncoder().encodeToString(post.getPlantImage());
                postResponse.setBase64Image(encodeToString);
            }

            Integer isLiked = likesRepository.isUserAlreadyLiked(post.getPostUUID(),userUUID);
            if(isLiked == 1)
            {
                postResponse.setLikedByUser(true);
            }else
            {
                postResponse.setLikedByUser(false);
            }
            postResponsesList.add(postResponse);
        }
        return postResponsesList;
    }

    public Post addPost(Post post, MultipartFile file ) throws IOException {
        post.setLikesCount(0);
        post.setTimestamp(Date.from(Instant.now()));


        if(file!=null){
                post.setFileExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
                post.setPlantImage(file.getBytes());

            }

        return postRepository.save(post);
    }

    public Post getPost(UUID postUUID) {

        Optional<Post> post = postRepository.findById(postUUID);
        Post responsePost = post.get();
        if(responsePost.getPlantImage()!=null){
            String encodeToString = "data:image/"+responsePost.getFileExtension()+";base64," + Base64.getEncoder().encodeToString(responsePost.getPlantImage());
            responsePost.setBase64Image(encodeToString);
        }

        return responsePost;
    }


    public int checkPlantTypeExists() {

        int checkTableSize = plantTypeRepository.findPlantTypeTableSize();
        int rowsInserted = 0;

        if (checkTableSize == DEFAULT_TABLE_SIZE) {

            for (int i = 0; i < PLANT_TYPES.size(); i++) {
                uuid = UUID.randomUUID();
                plantTypeRepository.insertPlantType(uuid.toString(), PLANT_TYPES.get(i));
            }
            rowsInserted = plantTypeRepository.findPlantTypeTableSize();
        }
        return rowsInserted;
    }

    public int checkPostTypeExists() {

        int checkTableSize = postTypeRepository.findPostTypeTableSize();
        int finalTableSize = 0;

        if (checkTableSize == DEFAULT_TABLE_SIZE) {

            for (int i = 0; i < POST_TYPES.size(); i++) {
                uuid = UUID.randomUUID();
                postTypeRepository.insertPostType(uuid.toString(), POST_TYPES.get(i));
            }
            finalTableSize = postTypeRepository.findPostTypeTableSize();
        }
        return finalTableSize;
    }



    public String deletePostById(UUID postUUID) throws EmptyResultDataAccessException {
        String result;
        try{
            postRepository.deleteById(postUUID);
            result = "Post deleted successfully";
        }catch(Exception exception){
            result = "Exception:"+exception;
        }
        return result;
    }

    public List<PlantType> getPlantType() {


        List<PlantType> plantTypes = plantTypeRepository.findAll();
        //                entityManager.createQuery(PLANT_TYPE_QUERY).getResultList();
        return plantTypes;

    }

    public List<PostType> getPostType() {

        List<PostType> postTypes = postTypeRepository.findAll();
//                entityManager.createQuery(POST_TYPE_QUERY).getResultList();
        return postTypes;

    }


}
