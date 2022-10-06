package com.oj.controller;




import com.oj.annoation.SystemLog;
import com.oj.pojo.vo.ArticleVo;
import com.oj.service.ArticleService;
import com.oj.utils.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 文章表(Article)表控制层
 *
 * @author makejava
 * @since 2022-04-03 18:55:17
 */
@RestController
@Api(tags = "评论")
@RequestMapping("/article")
public class ArticleController  {


    @Autowired
    private ArticleService articleService;

//    @GetMapping("/test1")
//    public List<Article> test(){
//       return service.list();
//    }

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){

        ResponseResult responseResult = articleService.hotArticleList();

        return responseResult;
    }

    @GetMapping("/articleList")
    @SystemLog(BusinessName = "文章列表")
    public ResponseResult ArticleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id ){

        return articleService.updateViewCount(id);
    }

    @PostMapping("/commitArticle")
    public ResponseResult commitArticle(@RequestBody ArticleVo article){

        return articleService.commitArticle(article);
    }

    @GetMapping ("/queryMyArticles/{userId}")
    public ResponseResult queryMyArticles(@PathVariable("userId") Long useId){

        return articleService.queryMyArticles(useId);
    }

    @DeleteMapping ("/deleteMyArticle/{articleId}")
    public ResponseResult deleteMyArticle(@PathVariable("articleId") Long articleId){

        return articleService.deleteMyArticle(articleId);
    }
}

