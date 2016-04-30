package com.riotgames.interview.hongkong.matchmaking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SampleData {

    public static List<Player> players = new ArrayList<Player>();

    public static double averageRating = 0;

    /**
     * The average win rating of all the players in the sample data. Helpful for when creating new players as we can
     * try and match them against an 'average' player (or not? =)).
     * <p>
     * We also use this value in order to calculate our 'Dumb Base Elo Rating' for all the players who have already
     * played matches.
     *
     * @return A float representing the mean / average rating of all players from the sample data
     */
    public static double averageWinRating() {

        if (averageRating == 0) {

            double totalRating = 0;
            for (Player player : getPlayers()) {

                totalRating += player.getWinRatio();
            }

            averageRating = totalRating / getPlayers().size();
        }

        return averageRating;
    }

    public static List<Player> getPlayers() {

        if (players.size() == 0) {
            final List<Player> players = new ArrayList<Player>(250);

            players.add(new Player("Kate Wells", 961, 658));
            players.add(new Player("Kristine Newman", 852, 179));
            players.add(new Player("Billie Roberts", 942, 951));
            players.add(new Player("Ernestine Holloway", 61, 500));
            players.add(new Player("Candace Reynolds", 235, 8));
            players.add(new Player("Susan Potter", 234, 486));
            players.add(new Player("Jessie Dawson", 910, 258));
            players.add(new Player("Clint Massey", 647, 310));
            players.add(new Player("Pamela Gross", 331, 599));
            players.add(new Player("Roxanne Morrison", 235, 980));
            players.add(new Player("Marjorie Tyler", 746, 570));
            players.add(new Player("Jeanette Bass", 909, 612));
            players.add(new Player("Guadalupe Saunders", 820, 430));
            players.add(new Player("Laura Barton", 908, 422));
            players.add(new Player("Simon Ingram", 99, 842));
            players.add(new Player("Dewey Fitzgerald", 3, 328));
            players.add(new Player("Rickey Lindsey", 665, 181));
            players.add(new Player("Harold Grant", 820, 70));
            players.add(new Player("Irvin Moody", 433, 98));
            players.add(new Player("Hannah Sullivan", 0, 499));
            players.add(new Player("Clark Jones", 986, 424));
            players.add(new Player("Jamie Parks", 371, 215));
            players.add(new Player("Sherri Thomas", 266, 33));
            players.add(new Player("Constance Day", 519, 661));
            players.add(new Player("Mae Mathis", 919, 165));
            players.add(new Player("Pam Alexander", 30, 784));
            players.add(new Player("Fannie Rios", 625, 162));
            players.add(new Player("Darrell Howard", 145, 457));
            players.add(new Player("Kathy Green", 675, 546));
            players.add(new Player("Elisa Hunt", 985, 296));
            players.add(new Player("Ricardo Rivera", 224, 289));
            players.add(new Player("Karen Reese", 641, 660));
            players.add(new Player("Roderick Jimenez", 754, 378));
            players.add(new Player("Abel Cruz", 739, 104));
            players.add(new Player("Clay Adams", 93, 813));
            players.add(new Player("Frank Nash", 102, 781));
            players.add(new Player("Jackie Allison", 162, 199));
            players.add(new Player("Celia Garcia", 485, 918));
            players.add(new Player("Sheldon Martin", 104, 29));
            players.add(new Player("Rex Clarke", 891, 468));
            players.add(new Player("Ross Castillo", 229, 230));
            players.add(new Player("Mathew Park", 650, 310));
            players.add(new Player("Pat Allen", 347, 365));
            players.add(new Player("Luke Perkins", 460, 972));
            players.add(new Player("Kurt Obrien", 733, -760));
            players.add(new Player("Pedro Stokes", 264, 360));
            players.add(new Player("Sophia Butler", 976, 376));
            players.add(new Player("Elaine Gregory", 630, 994));
            players.add(new Player("Eduardo Patrick", 479, 837));
            players.add(new Player("James Drake", 350, 487));
            players.add(new Player("Rogelio Caldwell", 174, 612));
            players.add(new Player("Stephanie Sutton", 770, 925));
            players.add(new Player("Mike Harmon", 656, 544));
            players.add(new Player("Lorene Wolfe", 235, 166));
            players.add(new Player("Irving Houston", 852, 11));
            players.add(new Player("Valerie Watson", 961, 22));
            players.add(new Player("Maggie Cortez", 242, 887));
            players.add(new Player("Johnnie Wallace", 965, 514));
            players.add(new Player("Anna Mckenzie", 628, 894));
            players.add(new Player("Wesley Carroll", 608, 343));
            players.add(new Player("Tara Harvey", 631, 138));
            players.add(new Player("Jo Ramos", 839, 868));
            players.add(new Player("Cary Pierce", 673, 586));
            players.add(new Player("Ellen Beck", 502, 990));
            players.add(new Player("Kristin Watts", 892, 57));
            players.add(new Player("Lewis Johnston", 960, 38));
            players.add(new Player("Denise Warner", 248, 100));
            players.add(new Player("Marcos Freeman", 601, 608));
            players.add(new Player("Joshua Chapman", 447, 880));
            players.add(new Player("Johnnie Carter", 643, 563));
            players.add(new Player("Nichole May", 98, 590));
            players.add(new Player("Bernard Mckinney", 212, 112));
            players.add(new Player("Robin Richardson", 546, 149));
            players.add(new Player("Fernando Warren", 169, 57));
            players.add(new Player("Carl Snyder", 336, 554));
            players.add(new Player("Ramon Huff", -99, 639));
            players.add(new Player("Felipe Silva", 245, 685));
            players.add(new Player("Della Rose", 864, 192));
            players.add(new Player("Jake Baker", 573, 379));
            players.add(new Player("Jennifer Tran", 493, 435));
            players.add(new Player("Earnest Hicks", 358, 652));
            players.add(new Player("Traci Harrington", 103, 569));
            players.add(new Player("Justin Powers", 264, 769));
            players.add(new Player("Lindsey Simpson", 985, 330));
            players.add(new Player("Desiree Rhodes", 876, 324));
            players.add(new Player("David Johnson", 191, 473));
            players.add(new Player("Lois Mann", 206, 547));
            players.add(new Player("Cesar Yates", 121, 325));
            players.add(new Player("Rachael Lee", 839, 450));
            players.add(new Player("Jeff Quinn", 259, 77));
            players.add(new Player("Olive Bush", 755, 220));
            players.add(new Player("Mandy Russell", 401, 672));
            players.add(new Player("Mildred Rogers", 618, 718));
            players.add(new Player("Peter Myers", 432, 358));
            players.add(new Player("Austin Chavez", 442, 576));
            players.add(new Player("Alex Bowers", 200, 854));
            players.add(new Player("Teresa Cannon", 801, 857));
            players.add(new Player("Antonia Lloyd", 741, 876));
            players.add(new Player("Melba Ford", 936, 37));
            players.add(new Player("Lynda Maldonado", 853, 398));
            players.add(new Player("Kelly Steele", 979, 766));
            players.add(new Player("Rosemarie Christensen", 52, 700));
            players.add(new Player("Sandy Warner", 740, 261));
            players.add(new Player("Christie Ward", 122, 707));
            players.add(new Player("Julie Henderson", 792, 155));
            players.add(new Player("Loretta Fletcher", 615, 92));
            players.add(new Player("Yvonne Ferguson", 450, 216));
            players.add(new Player("Ruth Soto", 669, 85));
            players.add(new Player("Virginia Tyler", 939, 743));
            players.add(new Player("Jenna Stokes", 787, 563));
            players.add(new Player("Michele Reed", 107, 228));
            players.add(new Player("Horace Barber", 666, 490));
            players.add(new Player("Ralph Ray", 624, 51));
            players.add(new Player("Ana Clarke", 743, 228));
            players.add(new Player("Donna Harmon", 781, 247));
            players.add(new Player("Laura Black", 113, 697));
            players.add(new Player("Clyde Chapman", 487, 243));
            players.add(new Player("Cory Bishop", 565, 241));
            players.add(new Player("Grant Chandler", 801, 952));
            players.add(new Player("Brenda Davis", 966, 587));
            players.add(new Player("Roderick Bailey", 71, 204));
            players.add(new Player("Lester Tucker", 280, 353));
            players.add(new Player("Arturo Goodman", 919, 19));
            players.add(new Player("Pedro Mccormick", 43, 491));
            players.add(new Player("Lee Potter", 131, 133));
            players.add(new Player("Kristine Mann", 340, 852));
            players.add(new Player("Mack Gonzales", 27, 692));
            players.add(new Player("Patrick Mills", 751, 268));
            players.add(new Player("Ian Poole", 128, 479));
            players.add(new Player("Daniel Moss", 553, 523));
            players.add(new Player("Ann Owen", 9223372036854775807L, 56));
            players.add(new Player("Antoinette Fuller", 242, 879));
            players.add(new Player("Flora Webster", 76, 191));
            players.add(new Player("Meghan Lawson", 952, 747));
            players.add(new Player("Traci Harris", 687, 809));
            players.add(new Player("Moses Walters", 678, 252));
            players.add(new Player("Cary Stone", 544, 521));
            players.add(new Player("Deborah Nelson", 891, 736));
            players.add(new Player("Julio Hale", 855, 704));
            players.add(new Player("Erin Goodwin", 335, 829));
            players.add(new Player("Bethany Johnson", 567, 652));
            players.add(new Player("Leah Patton", 17, 884));
            players.add(new Player("Merle Hansen", 752, 315));
            players.add(new Player("Jennifer Love", 803, 440));
            players.add(new Player("Christy Berry", 372, 90));
            players.add(new Player("Nick Mccoy", 703, 895));
            players.add(new Player("Candace Hawkins", 767, 278));
            players.add(new Player("Mamie Spencer", 803, 523));
            players.add(new Player("Ruby Zimmerman", 743, 934));
            players.add(new Player("Doris Harrison", 279, 61));
            players.add(new Player("Michelle Fisher", 715, 683));
            players.add(new Player("Wade Carter", 863, 72));
            players.add(new Player("Dianne Howard", 192, 173));
            players.add(new Player("Bernard Wheeler", 708, 445));
            players.add(new Player("Paulette Jennings", 134, 316));
            players.add(new Player("Kerry Silva", 669, 128));
            players.add(new Player("Hilda Terry", 506, 944));
            players.add(new Player("Salvatore Hart", 228, 926));
            players.add(new Player("Terry Carpenter", 901, 605));
            players.add(new Player("Marie Jackson", 739, 842));
            players.add(new Player("Alice Reese", 113, 518));
            players.add(new Player("Monique Schmidt", 412, 795));
            players.add(new Player("Marvin Hubbard", 155, 60));
            players.add(new Player("Dexter Estrada", 312, 778));
            players.add(new Player("Dianna Obrien", 724, 932));
            players.add(new Player("Olga Washington", 367, 709));
            players.add(new Player("Lora Brock", 545, 547));
            players.add(new Player("Sheldon Hill", 262, 292));
            players.add(new Player("Leroy Myers", 187, 798));
            players.add(new Player("Jo Park", 999, 949));
            players.add(new Player("Bill Ortega", 521, 794));
            players.add(new Player("Vicki Rios", 610, 544));
            players.add(new Player("Jean Douglas", 417, 4));
            players.add(new Player("Harriet Morrison", 882, 417));
            players.add(new Player("Ray Lindsey", 109, 607));
            players.add(new Player("Tom Greene", 130, 781));
            players.add(new Player("Nicholas Davidson", 482, 432));
            players.add(new Player("Ronald Castillo", 861, 354));
            players.add(new Player("Robin Jefferson", 204, 431));
            players.add(new Player("Frances Summers", 549, 672));
            players.add(new Player("Debbie Cox", 246, 332));
            players.add(new Player("Christian Warren", 188, 510));
            players.add(new Player("Kristopher Turner", 0, 0));
            players.add(new Player("Oscar Houston", 286, 171));
            players.add(new Player("Charlie Webb", 204, 383));
            players.add(new Player("Todd Montgomery", 712, 914));
            players.add(new Player("Irving Franklin", 346, 62));
            players.add(new Player("Jacob Brown", 1, 337));
            players.add(new Player("Jermaine Sandoval", 39, 910));
            players.add(new Player("Jeannette Francis", 966, 318));
            players.add(new Player("Brian Dunn", 726, 532));
            players.add(new Player("Brandon Walsh", 108, 477));
            players.add(new Player("Adrian Griffin", 572, 596));
            players.add(new Player("Martha Pierce", 774, 995));
            players.add(new Player("Estelle Mendoza", 991, 674));
            players.add(new Player("Mary Blake", 103, 552));
            players.add(new Player("Alberto Watts", 65, 748));
            players.add(new Player("Kent Johnston", 609, 205));
            players.add(new Player("Clifton Mendez", 652, 562));
            players.add(new Player("Conrad Stanley", 228, 74));
            SampleData.players = players;
        }

        return SampleData.players;
    }

    public static List<Player> getPlayersSortedByWinRatio() {

        Collections.sort(SampleData.getPlayers(), new PlayerComparatorWinRatio());
        return SampleData.players;
    }

    public static List<Player> getPlayersSortedByElo() {

        Collections.sort(SampleData.getPlayers(), new PlayerComparatorElo());
        return SampleData.players;
    }
}