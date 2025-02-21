package com.minimal_not_a_bot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.minimal_not_a_bot.model.BlogPost;
import com.minimal_not_a_bot.service.BlogPostService;
import com.minimal_not_a_bot.util.HashUtil;

@SpringBootTest
public class BlogPostServiceTests {

    private static final String AGOT_KEY = "A GAME OF THRONES";
    private static final String TWOW_KEY = "THE WINDS OF WINTER";
    private static final String ADOS_KEY = "A DREAM OF SPRING";
    private static final String BAF_KEY = "BLOOD & FIRE";

    private BlogPost blogPost;

    @BeforeEach
    void init() throws IOException {
        Path path = Paths.get("src/test/resources/blog-posts/here-there-be-dragon-11-july-2024.html");
        String htmlContent = Files.readString(path);

        Document document = Jsoup.parse(htmlContent);
        blogPost = BlogPostService.blogPostBuilder(document.body());
    }

    @Test
    void checkBlogPostTitle() {
        String expectedBlogPostTitle = "Here There Be Dragons";
        assertEquals(expectedBlogPostTitle, blogPost.getTitle());
    }

    @Test
    void checkBlogPostURL() {
        String expectedBlogPostURL = "https://georgerrmartin.com/notablog/2024/07/11/here-there-be-dragons-2/";
        assertEquals(expectedBlogPostURL, blogPost.getUrl());
    }

    @Test
    void checkBlogPostTheDate() {
        String expectedBlogPostTheDate = "July 11, 2024";
        assertEquals(expectedBlogPostTheDate, blogPost.getTheDate());
    }

    @Test
    void checkBlogPostDate() {
        LocalDateTime expectedBlogPostDate = LocalDateTime.of(2024, 07, 11, 0, 0);
        assertEquals(expectedBlogPostDate, blogPost.getTheDateFormatted());
    }

    @Test
    void checkBlogPostImage() {
        String expectedBlogPostImage = "https://georgerrmartin.com/notablog/wp-content/uploads/2023/01/Screen-Shot-2023-01-18-at-1.08.05-PM-300x214.png";
        assertEquals(expectedBlogPostImage, blogPost.getProfileImage());
    }

    @Test
    void checkBlogPostContentParagraph01() {
        String expectedBlogPostContentParagraph = "I trust you all caught “The Red Dragon and the Gold,” the fourth episode of season 2 of H<em>OUSE OF THE DRAGON</em>. A lot of you have been wanting for action, I know; this episode delivered it in spades with the Battle of Rook’s Rest, when dragon met dragon in the skies.";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(0));
    }

    @Test
    void checkBlogPostContentParagraph02() {
        String expectedBlogPostContentParagraph = "Has there ever been a dragon battle to match it? I seem to recall that <em>REIGN OF FIRE</em> had a few scenes where a dozen dragons were wheeling through the skies. So, okay, maybe that was a <strong><em>bigger</em></strong> scene, with more dragons on screen… but a <em><strong>better</strong></em> battle? I don’t think so. Our guys knocked this one out of the castle. I think they took it as a challenge. And the dragons…";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(1));
    }

    @Test
    void checkBlogPostContentParagraph03() {
        String expectedBlogPostContentParagraph = "Dragons are mythical, of course. In the real world, the one we live in as opposed to those we like to read about… dragons never existed… though similar creatures can be found in legends all around the world. Some believe that maybe the stories were inspired by the discovery of dinosaur bones by farmers plowing their fields. Regardless of where the stories originated, they have been a huge part of fantasy for centuries. And I’ve been fond of them for as long as I remember.";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(2));
    }

    @Test
    void checkBlogPostContentParagraph04() {
        String expectedBlogPostContentParagraph = "Hell, I’m <i>named</i> after a dragonslayer — St. George, of course — and he’s still a saint, when a lot of other saints were thrown out a couple decades back… which I suppose means that dragons have papal approval. I started writing my own dragon tales long before <em>A GAME OF THRONES.</em> “The Ice Dragon” and “The Way of Cross and Dragon” were two of my best.";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(3));
    }

    @Test
    void checkBlogPostContentParagraph05() {
        String expectedBlogPostContentParagraph = "Every culture has its own version of dragons; Chinese dragons are wingless and do not breathe fire. They bring good luck. Traditional western dragons bring mostly fire and death… but modern fantasists have played with that a lot too. The dragons of<em> ERAGON</em> and <em>HOW TO TRAIN YOUR DRAGON</em> are very different from mine own. (Toothless is even cute).";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(4));
    }

    @Test
    void checkBlogPostContentParagraph06() {
        String expectedBlogPostContentParagraph = "<img src=\"https://georgerrmartin.com/notablog/wp-content/uploads/2024/07/toothless.jpg\"/>";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(5));
    }

    @Test
    void checkBlogPostContentParagraph07() {
        String expectedBlogPostContentParagraph = "Tolkien’s dragons were always evil, servants of Morgoth and Sauron. They were akin to his orcs and trolls. JRRT did not do friendly dragons. His dragons were intelligent, though. Smaug <em>talks</em>. He also has a huge horde of gold, a very traditional dragon trait… and he sleeps on his treasure, for months and years at a time.";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(6));
    }

    @Test
    void checkBlogPostContentParagraph08() {
        String expectedBlogPostContentParagraph = "<img src=\"https://georgerrmartin.com/notablog/wp-content/uploads/2024/07/smaug_dragon-1.webp\"/>";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(7));
    }

    @Test
    void checkBlogPostContentParagraph09() {
        String expectedBlogPostContentParagraph = "Before Peter Jackson’s Smaug, the best dragon ever seen on film was Vermithrax Pejorative in <em>DRAGONSLAYER</em><b><i>.</i></b> Two legs and two wings, dangerous, fire-breathing, a flyer, does not talk, does not horde gold. An inspiration for all dragonlovers.";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(8));
    }

    @Test
    void checkBlogPostContentParagraph10() {
        String expectedBlogPostContentParagraph = "<img src=\"https://georgerrmartin.com/notablog/wp-content/uploads/2024/06/verm1-1024x691.jpg\"/>";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(9));
    }

    @Test
    void checkBlogPostContentParagraph11() {
        String expectedBlogPostContentParagraph = "At the other end of the scale is the dragon in <b><i>DRAGONHEART</i></b> (voice by Sean Connery). Fat, four-legged, talking, a good guy who befriends the hero. A much inferior dragon in a much inferior film. Bah.";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(10));
    }

    @Test
    void checkBlogPostContentParagraph12() {
        String expectedBlogPostContentParagraph = "<img src=\"https://georgerrmartin.com/notablog/wp-content/uploads/2024/07/Dragonheart-1.jpg\"/>";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(11));
    }

    @Test
    void checkBlogPostContentParagraph13() {
        String expectedBlogPostContentParagraph = "In <em>A SONG OF ICE & FIRE,</em> I set out to blend the wonder of epic fantasy with the grittiness of the best historical fiction. There is magic in my world, yes… but much less of it than one gets in most fantasy. (Tolkien’s Middle Earth was relatively low magic too, and I took my cue from the master). I wanted Westeros to feel <em>real</em>, to evoke the Crusades and the Hundred Years War and the Wars of the Roses as much as it did JRRT with his hobbits and magic rings.";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(12));
    }

    @Test
    void checkBlogPostContentParagraph14() {
        String expectedBlogPostContentParagraph = "I would have dragons, yes… in part because of my dear friend, the late Phyllis Eisenstein, a marvelous fantasist and science fiction writer in her own right, now sadly missed… but I wanted my dragons to be as real and believable as such a creature could ever be. I designed my dragons with a lot of care. They fly and breathe fire, yes, those traits seemed essential to me. They have<strong><em> two</em></strong> legs (not four, never four) and two wings.<br><em>LARGE</em> wings. A lot of fantasy dragons have these itty bitty wings that would never get such a creature off the ground. <strong>And only two legs; the wings are the forelegs.</strong> Four-legged dragons exist only in heraldry. No animal that has ever lived on Earth has six limbs. Birds have two legs and two wings, bats the same, ditto pteranodons and other flying dinosaurs, etc.";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(13));
    }

    @Test
    void checkBlogPostContentParagraph15() {
        String expectedBlogPostContentParagraph = "Much of the confusion about the proper number of legs on a dragon has its roots in medieval heraldry. In the beginning both versions could be seen on shields and banners, but over the centuries, as heraldry became more standardized, the heralds took to calling the four-legged beasties <strong><em>dragon</em></strong>s and their two-legged kin <strong><em>wyverns</em></strong>. No one had ever<em> seen</em> a dragon or a wyvern, of course; neither creature actually existed save in legend, so there was a certain arbitrary quality to this distinction… and medieval heralds were not exactly renowned for their grasp of zoology, even for real world animals. Just take a look at what they thought a seahorse looked like.";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(14));
    }

    @Test
    void checkBlogPostContentParagraph16() {
        String expectedBlogPostContentParagraph = "Dragons <em><strong>DO</strong></em> exist in the world of Westeros, however (wyverns too, down in Sothoryos), so my own heralds did not have that excuse. Ergo, in my books, the Targaryen sigil has two legs, as it should. Why would any Westerosi ever put four legs on a dragon, when they could look at the real thing and could their limbs? My wyverns have two legs as well; they differ from the dragons of my world chiefly in size, coloration, and the inability to breath fire. (It should be stressed that while the Targaryen sigil has the proper number of legs (two), it is not exactly anatomically correct. The wings are way too small compared to the body, and of course no dragon has three heads. That bit is purely symbolic, meant to reflect Aegon the Conqueror and his two sisters).";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(15));
    }

    @Test
    void checkBlogPostContentParagraph17() {
        String expectedBlogPostContentParagraph = "FWIW, the shows got it half right (both of them). GAME OF THRONES gave us the correct two-legged sigils for the first four seasons and most of the fifth, but when Dany’s fleet hove into view, all the sails showed four-legged dragons. Someone got sloppy, I guess. Or someone opened a book on heraldry, and read just enough of it to muck it all up. A little knowledge is a dangerous thing. A couple years on, HOUSE OF THE DRAGON decided the heraldry should be consistent with GAME OF THRONES.. but they went with the bad sigil rather than the good one. That sound you heard was me screaming, “no, no, no.” Those damned extra legs have even wormed their way onto the covers of my books, over my strenuous objections.";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(16));
    }

    @Test
    void checkBlogPostContentParagraph18() {
        String expectedBlogPostContentParagraph = "<strong>RIGHT</strong>";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(17));
    }

    @Test
    void checkBlogPostContentParagraph19() {
        String expectedBlogPostContentParagraph = "<img src=\"https://georgerrmartin.com/notablog/wp-content/uploads/2024/06/T-2-sigil.png\"/>";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(18));
    }

    @Test
    void checkBlogPostContentParagraph20() {
        String expectedBlogPostContentParagraph = "<strong>WRONG</strong>";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(19));
    }

    @Test
    void checkBlogPostContentParagraph21() {
        String expectedBlogPostContentParagraph = "<img src=\"https://georgerrmartin.com/notablog/wp-content/uploads/2024/06/T-4-sigil.jpg\"/>";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(20));
    }

    @Test
    void checkBlogPostContentParagraph22() {
        String expectedBlogPostContentParagraph = "Valyrian dragons differ in other ways from the likes of Smaug and Toothless and Vermithrax as well.";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(21));
    }

    @Test
    void checkBlogPostContentParagraph23() {
        String expectedBlogPostContentParagraph = "My dragons do not talk. They are relatively intelligent, but they are still beasts.";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(22));
    }

    @Test
    void checkBlogPostContentParagraph24() {
        String expectedBlogPostContentParagraph = "They bond with men… some men… and the why and how of that, and how it came to be, will eventually be revealed in more detail in <em>THE WINDS OF WINTER</em> and <em>A DREAM OF SPRING</em> and some in <em>BLOOD & FIRE</em>. (Septon Barth got much of it right). Like wolves and bears and lions, dragons can be trained, but never entirely tamed. They will always be dangerous. Some are wilder and more wilful than others. They are individuals, they have personalities… and they often reflect the personalities of their riders, thanks to bond they share are. They do not care a whit about gold or gems, no more than a tiger would. Unless maybe their rider was obsessed with the shiny stuff, and even then…";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(23));
    }

    @Test
    void checkBlogPostContentParagraph25() {
        String expectedBlogPostContentParagraph = "Dragons need food. They need water too, but they have no gills. They need to breathe . Some say that Smaug slept for sixty years below the Lonely Mountains before Bilbo and the dwarves woke him up. The dragons born of Valyria cannot do that. They are creatures of fire, and fire needs oxygen. A dragon could dip into the ocean to scoop up a fish, perhaps, but they’d fly right up again. If held underwater too long, they would drown, just like any other land animal.";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(24));
    }

    @Test
    void checkBlogPostContentParagraph26() {
        String expectedBlogPostContentParagraph = "My dragons are predators, carnivores who like their meat will done. They can and will hunt their own prey, but they are also territorial. They have lairs. As creatures of the sky, they like mountain tops, and volcanic mountains best of all. These are creatures of fire, and the cold dank caverns that other fantasists house their pets in are not for mine. Man-made dwellings, like the stables of Dragonstone, the towers tops of the Valryian Freehold, and the Dragonpit of King’s Landing, are acceptable — and often come with men bringing them food. If those are not available, young dragons will find their own lairs… and defend them fiercely.";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(25));
    }

    @Test
    void checkBlogPostContentParagraph27() {
        String expectedBlogPostContentParagraph = "My dragons are creatures of the sky. They fly, and can cross mountains and plains, cover hundreds of miles… but they don’t, unless their riders take them there. They are not nomadic. During the heyday of Valyria there were forty dragon-riding families with hundreds of dragons amongst them… but (aside from our Targaryens) all of them stayed close to the Freehold and the Lands of the Long Summer. From time to time a dragonrider might visit Volantis or another Valyrian colony, even settle there for a few years, but never permanently. Think about it. If dragons were nomadic, they would have overrun half of Essos, and the Doom would only have killed a few of them. Similarly, the dragons of Westeros seldom wander far from Dragonstone. Elsewise, after three hundred years, we would have dragons all over the realm and every noble house would have a few. The three wild dragons mentioned in <b><i>Fire & Blood</i></b> have lairs on Dragonstone. The rest can be found in the Dragonpit of King’s Landing, or in deep caverns under the Dragonmont. Luke flies Arrax to Storm’s End and Jace to Winterfell, yes, but the dragons would not have flown there on their own, save under very special circumstances. You won’t find dragons hunting the riverlands or the Reach or the Vale, or roaming the northlands or the mountains of Dorne.";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(26));
    }

    @Test
    void checkBlogPostContentParagraph28() {
        String expectedBlogPostContentParagraph = "Fantasy needs to be grounded. It is not simply a license to do anything you like. Smaug and Toothless may both be dragons, but they should never be confused. Ignore canon, and the world you’ve created comes apart like tissue paper.";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(27));
    }

    @Test
    void checkBlogPostContentParagraph29() {
        String expectedBlogPostContentParagraph = "<b>Current Mood:</b> <img src=\"https://georgerrmartin.com/notablog/wp-content/themes/dark-shop-lite/emojis/thoughtful.gif\" alt=\"thoughtful\"> thoughtful";
        assertEquals(expectedBlogPostContentParagraph, blogPost.getContent().get(28));
    }

    @Test
    void checkBlogPostTags() {
        String expectedBlogPostTags = "Tags: a song of ice and fire, canon, dragons, fire & blood";
        assertEquals(expectedBlogPostTags, blogPost.getTags());
    }

    @Test
    void checkASOIAFBooksMentions() {
        String expectedParagraphMentionsAGOT = "Hell, I’m <i>named</i> after a dragonslayer — St. George, of course — and he’s still a saint, when a lot of other saints were thrown out a couple decades back… which I suppose means that dragons have papal approval. I started writing my own dragon tales long before <em>A GAME OF THRONES.</em> “The Ice Dragon” and “The Way of Cross and Dragon” were two of my best.";
        String expectedParagraphMentionsTWOW_ADOS_BAF = "They bond with men… some men… and the why and how of that, and how it came to be, will eventually be revealed in more detail in <em>THE WINDS OF WINTER</em> and <em>A DREAM OF SPRING</em> and some in <em>BLOOD & FIRE</em>. (Septon Barth got much of it right). Like wolves and bears and lions, dragons can be trained, but never entirely tamed. They will always be dangerous. Some are wilder and more wilful than others. They are individuals, they have personalities… and they often reflect the personalities of their riders, thanks to bond they share are. They do not care a whit about gold or gems, no more than a tiger would. Unless maybe their rider was obsessed with the shiny stuff, and even then…";

        Map<String, List<String>> expectedASOIAFBooksMentions = Map.of(AGOT_KEY,
                List.of(expectedParagraphMentionsAGOT),
                TWOW_KEY, List.of(expectedParagraphMentionsTWOW_ADOS_BAF),
                ADOS_KEY, List.of(expectedParagraphMentionsTWOW_ADOS_BAF),
                BAF_KEY, List.of(expectedParagraphMentionsTWOW_ADOS_BAF));

        assertEquals(expectedASOIAFBooksMentions, blogPost.getASOIAFBooksMentions());
    }

    @Test
    void checkBlogPostHashCode() {
        String blogPostTitle = "Here There Be Dragons";
        String blogPostTheDate = "July 11, 2024";
        String blogPostURL = "https://georgerrmartin.com/notablog/2024/07/11/here-there-be-dragons-2/";
        String expectedBlogPostHashCode = HashUtil.generateHash(blogPostTitle, blogPostTheDate, blogPostURL);

        assertEquals(expectedBlogPostHashCode, blogPost.getPostHashCode());
    }
}
