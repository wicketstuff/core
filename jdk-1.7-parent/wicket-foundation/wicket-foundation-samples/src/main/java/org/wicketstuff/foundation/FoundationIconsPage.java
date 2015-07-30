package org.wicketstuff.foundation;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.foundation.blockgrid.BlockGridOptions;
import org.wicketstuff.foundation.blockgrid.BlockGridType;
import org.wicketstuff.foundation.blockgrid.FoundationBlockGrid;
import org.wicketstuff.foundation.icon.IconType;
import org.wicketstuff.foundation.util.StringUtil;

public class FoundationIconsPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public FoundationIconsPage(PageParameters params) {
		super(params);
		
		ArrayList<BlockGridOptions> optionsList = new ArrayList<>();
		optionsList.add(new BlockGridOptions(BlockGridType.SMALL_BLOCK_GRID, 2));
		optionsList.add(new BlockGridOptions(BlockGridType.LARGE_BLOCK_GRID, 4));
		
		ArrayList<IconType> generalIconsDataList = new ArrayList<>();
		generalIconsDataList.add(IconType.HEART);
		generalIconsDataList.add(IconType.STAR);
		generalIconsDataList.add(IconType.PLUS);
		generalIconsDataList.add(IconType.MINUS);
		generalIconsDataList.add(IconType.X);
		generalIconsDataList.add(IconType.CHECK);
		generalIconsDataList.add(IconType.UPLOAD);
		generalIconsDataList.add(IconType.DOWNLOAD);
		generalIconsDataList.add(IconType.WIDGET);
		generalIconsDataList.add(IconType.MARKER);
		generalIconsDataList.add(IconType.TRASH);
		generalIconsDataList.add(IconType.PAPERCLIP);
		generalIconsDataList.add(IconType.LOCK);
		generalIconsDataList.add(IconType.UNLOCK);
		generalIconsDataList.add(IconType.CALENDAR);
		generalIconsDataList.add(IconType.CLOUD);
		generalIconsDataList.add(IconType.MAGNIFYING_GLASS);
		generalIconsDataList.add(IconType.ZOOM_OUT);
		generalIconsDataList.add(IconType.ZOOM_IN);
		generalIconsDataList.add(IconType.WRENCH);
		generalIconsDataList.add(IconType.RSS);
		generalIconsDataList.add(IconType.SHARE);
		generalIconsDataList.add(IconType.FLAG);
		generalIconsDataList.add(IconType.LIST_THUMBNAILS);
		generalIconsDataList.add(IconType.LIST);
		generalIconsDataList.add(IconType.THUMBNAILS);
		generalIconsDataList.add(IconType.ANNOTATE);
		generalIconsDataList.add(IconType.FOLDER);
		generalIconsDataList.add(IconType.FOLDER_LOCK);
		generalIconsDataList.add(IconType.FOLDER_ADD);
		generalIconsDataList.add(IconType.CLOCK);
		generalIconsDataList.add(IconType.PLAY_VIDEO);
		generalIconsDataList.add(IconType.CROP);
		generalIconsDataList.add(IconType.ARCHIVE);
		generalIconsDataList.add(IconType.PENCIL);
		generalIconsDataList.add(IconType.GRAPH_TREND);
		generalIconsDataList.add(IconType.GRAPH_BAR);
		generalIconsDataList.add(IconType.GRAPH_HORIZONTAL);
		generalIconsDataList.add(IconType.GRAPH_PIE);
		generalIconsDataList.add(IconType.CHECKBOX);
		generalIconsDataList.add(IconType.MINUS_CIRCLE);
		generalIconsDataList.add(IconType.X_CIRCLE);
		generalIconsDataList.add(IconType.EYE);
		generalIconsDataList.add(IconType.DATABASE);
		generalIconsDataList.add(IconType.RESULTS);
		generalIconsDataList.add(IconType.RESULTS_DEMOGRAPHICS);
		generalIconsDataList.add(IconType.LIKE);
		generalIconsDataList.add(IconType.DISLIKE);
		generalIconsDataList.add(IconType.UPLOAD_CLOUD);
		generalIconsDataList.add(IconType.CAMERA);
		generalIconsDataList.add(IconType.ALERT);
		generalIconsDataList.add(IconType.BOOKMARK);
		generalIconsDataList.add(IconType.CONTRAST);
		generalIconsDataList.add(IconType.MAIL);
		generalIconsDataList.add(IconType.VIDEO);
		generalIconsDataList.add(IconType.TELEPHONE);
		generalIconsDataList.add(IconType.COMMENT);
		generalIconsDataList.add(IconType.COMMENT_VIDEO);
		generalIconsDataList.add(IconType.COMMENT_QUOTES);
		generalIconsDataList.add(IconType.COMMENT_MINUS);
		generalIconsDataList.add(IconType.MEGAPHONE);
		generalIconsDataList.add(IconType.SOUND);
		generalIconsDataList.add(IconType.ADDRESS_BOOK);
		generalIconsDataList.add(IconType.BLUETOOTH);
		generalIconsDataList.add(IconType.HTML5);
		generalIconsDataList.add(IconType.CSS3);
		generalIconsDataList.add(IconType.LAYOUT);
		generalIconsDataList.add(IconType.WEB);
		generalIconsDataList.add(IconType.FOUNDATION);
		add(createBlockGrid("generalIcons", optionsList, generalIconsDataList));
		
		ArrayList<IconType> pageIconsDataList = new ArrayList<>();
		pageIconsDataList.add(IconType.PAGE);
		pageIconsDataList.add(IconType.PAGE_CSV);
		pageIconsDataList.add(IconType.PAGE_DOC);
		pageIconsDataList.add(IconType.PAGE_PDF);
		pageIconsDataList.add(IconType.PAGE_EXPORT);
		pageIconsDataList.add(IconType.PAGE_EXPORT_CSV);
		pageIconsDataList.add(IconType.PAGE_EXPORT_DOC);
		pageIconsDataList.add(IconType.PAGE_EXPORT_PDF);
		pageIconsDataList.add(IconType.PAGE_ADD);
		pageIconsDataList.add(IconType.PAGE_REMOVE);
		pageIconsDataList.add(IconType.PAGE_DELETE);
		pageIconsDataList.add(IconType.PAGE_EDIT);
		pageIconsDataList.add(IconType.PAGE_SEARCH);
		pageIconsDataList.add(IconType.PAGE_COPY);
		pageIconsDataList.add(IconType.PAGE_FILLED);
		pageIconsDataList.add(IconType.PAGE_MULTIPLE);
		add(createBlockGrid("pageIcons", optionsList, pageIconsDataList));
		
		ArrayList<IconType> arrowIconsDataList = new ArrayList<>();
		arrowIconsDataList.add(IconType.ARROW_UP);
		arrowIconsDataList.add(IconType.ARROW_RIGHT);
		arrowIconsDataList.add(IconType.ARROW_DOWN);
		arrowIconsDataList.add(IconType.ARROW_LEFT);
		arrowIconsDataList.add(IconType.ARROWS_OUT);
		arrowIconsDataList.add(IconType.ARROWS_IN);
		arrowIconsDataList.add(IconType.ARROWS_EXPAND);
		arrowIconsDataList.add(IconType.ARROWS_COMPRESS);
		add(createBlockGrid("arrowIcons", optionsList, arrowIconsDataList));
		
		ArrayList<IconType> peopleIconsDataList = new ArrayList<>();
		peopleIconsDataList.add(IconType.TORSO);
		peopleIconsDataList.add(IconType.TORSO_FEMALE);
		peopleIconsDataList.add(IconType.TORSOS);
		peopleIconsDataList.add(IconType.TORSOS_FEMALE_MALE);
		peopleIconsDataList.add(IconType.TORSOS_MALE_FEMALE);
		peopleIconsDataList.add(IconType.TORSOS_ALL);
		peopleIconsDataList.add(IconType.TORSOS_ALL_FEMALE);
		peopleIconsDataList.add(IconType.TORSO_BUSINESS);
		add(createBlockGrid("peopleIcons", optionsList, peopleIconsDataList));
		
		ArrayList<IconType> deviceIconsDataList = new ArrayList<>();
		deviceIconsDataList.add(IconType.MONITOR);
		deviceIconsDataList.add(IconType.LAPTOP);
		deviceIconsDataList.add(IconType.TABLET_PORTRAIT);
		deviceIconsDataList.add(IconType.TABLET_LANDSCAPE);
		deviceIconsDataList.add(IconType.MOBILE);
		deviceIconsDataList.add(IconType.MOBILE_SIGNAL);
		deviceIconsDataList.add(IconType.USB);
		add(createBlockGrid("deviceIcons", optionsList, deviceIconsDataList));
		
		ArrayList<IconType> textEditorIconsDataList = new ArrayList<>();
		textEditorIconsDataList.add(IconType.BOLD);
		textEditorIconsDataList.add(IconType.ITALIC);
		textEditorIconsDataList.add(IconType.UNDERLINE);
		textEditorIconsDataList.add(IconType.STRIKETHROUGH);
		textEditorIconsDataList.add(IconType.TEXT_COLOR);
		textEditorIconsDataList.add(IconType.BACKGROUND_COLOR);
		textEditorIconsDataList.add(IconType.SUPERSCRIPT);
		textEditorIconsDataList.add(IconType.SUBSCRIPT);
		textEditorIconsDataList.add(IconType.ALIGN_LEFT);
		textEditorIconsDataList.add(IconType.ALIGN_CENTER);
		textEditorIconsDataList.add(IconType.ALIGN_RIGHT);
		textEditorIconsDataList.add(IconType.ALIGN_JUSTIFY);
		textEditorIconsDataList.add(IconType.LIST_NUMBERED);
		textEditorIconsDataList.add(IconType.LIST_BULLET);
		textEditorIconsDataList.add(IconType.INDENT_MORE);
		textEditorIconsDataList.add(IconType.INDENT_LESS);
		textEditorIconsDataList.add(IconType.PRINT);
		textEditorIconsDataList.add(IconType.SAVE);
		textEditorIconsDataList.add(IconType.PHOTO);
		textEditorIconsDataList.add(IconType.FILTER);
		textEditorIconsDataList.add(IconType.PAINT_BUCKET);
		textEditorIconsDataList.add(IconType.LINK);
		textEditorIconsDataList.add(IconType.UNLINK);
		textEditorIconsDataList.add(IconType.QUOTE);
		add(createBlockGrid("textEditorIcons", optionsList, textEditorIconsDataList));

		ArrayList<IconType> mediaControlIconsDataList = new ArrayList<>();
		mediaControlIconsDataList.add(IconType.PLAY);
		mediaControlIconsDataList.add(IconType.STOP);
		mediaControlIconsDataList.add(IconType.PAUSE);
		mediaControlIconsDataList.add(IconType.PREVIOUS);
		mediaControlIconsDataList.add(IconType.REWIND);
		mediaControlIconsDataList.add(IconType.FAST_FORWARD);
		mediaControlIconsDataList.add(IconType.NEXT);
		mediaControlIconsDataList.add(IconType.RECORD);
		mediaControlIconsDataList.add(IconType.PLAY_CIRCLE);
		mediaControlIconsDataList.add(IconType.VOLUME_NONE);
		mediaControlIconsDataList.add(IconType.VOLUME);
		mediaControlIconsDataList.add(IconType.VOLUME_STRIKE);
		mediaControlIconsDataList.add(IconType.LOOP);
		mediaControlIconsDataList.add(IconType.SHUFFLE);
		mediaControlIconsDataList.add(IconType.EJECT);
		mediaControlIconsDataList.add(IconType.REWIND_TEN);
		add(createBlockGrid("mediaControlIcons", optionsList, mediaControlIconsDataList));

		ArrayList<IconType> eCommerceIconsDataList = new ArrayList<>();
		eCommerceIconsDataList.add(IconType.DOLLAR);
		eCommerceIconsDataList.add(IconType.EURO);
		eCommerceIconsDataList.add(IconType.POUND);
		eCommerceIconsDataList.add(IconType.YEN);
		eCommerceIconsDataList.add(IconType.BITCOIN);
		eCommerceIconsDataList.add(IconType.BITCOIN_CIRCLE);
		eCommerceIconsDataList.add(IconType.CREDIT_CARD);
		eCommerceIconsDataList.add(IconType.SHOPPING_CART);
		eCommerceIconsDataList.add(IconType.BURST);
		eCommerceIconsDataList.add(IconType.BURST_NEW);
		eCommerceIconsDataList.add(IconType.BURST_SALE);
		eCommerceIconsDataList.add(IconType.PAYPAL);
		eCommerceIconsDataList.add(IconType.PRICE_TAG);
		eCommerceIconsDataList.add(IconType.PRICETAG_MULTIPLE);
		eCommerceIconsDataList.add(IconType.SHOPPING_BAG);
		eCommerceIconsDataList.add(IconType.DOLLAR_BILL);
		add(createBlockGrid("eCommerceIcons", optionsList, eCommerceIconsDataList));

		ArrayList<IconType> accessibilityIconsDataList = new ArrayList<>();
		accessibilityIconsDataList.add(IconType.WHEELCHAIR);
		accessibilityIconsDataList.add(IconType.BRAILLE);
		accessibilityIconsDataList.add(IconType.CLOSED_CAPTION);
		accessibilityIconsDataList.add(IconType.BLIND);
		accessibilityIconsDataList.add(IconType.ASL);
		accessibilityIconsDataList.add(IconType.HEARING_AID);
		accessibilityIconsDataList.add(IconType.GUIDE_DOG);
		accessibilityIconsDataList.add(IconType.UNIVERSAL_ACCESS);
		accessibilityIconsDataList.add(IconType.TELEPHONE_ACCESSIBLE);
		accessibilityIconsDataList.add(IconType.ELEVATOR);
		accessibilityIconsDataList.add(IconType.MALE);
		accessibilityIconsDataList.add(IconType.FEMALE);
		accessibilityIconsDataList.add(IconType.MALE_FEMALE);
		accessibilityIconsDataList.add(IconType.MALE_SYMBOL);
		accessibilityIconsDataList.add(IconType.FEMALE_SYMBOL);
		add(createBlockGrid("accessibilityIcons", optionsList, accessibilityIconsDataList));

		ArrayList<IconType> socialIconsDataList = new ArrayList<>();
		socialIconsDataList.add(IconType.SOCIAL_500PX);
		socialIconsDataList.add(IconType.SOCIAL_ADOBE);
		socialIconsDataList.add(IconType.SOCIAL_AMAZON);
		socialIconsDataList.add(IconType.SOCIAL_ANDROID);
		socialIconsDataList.add(IconType.SOCIAL_APPLE);
		socialIconsDataList.add(IconType.SOCIAL_BEHANCE);
		socialIconsDataList.add(IconType.SOCIAL_BING);
		socialIconsDataList.add(IconType.SOCIAL_BLOGGER);
		socialIconsDataList.add(IconType.SOCIAL_DELICIOUS);
		socialIconsDataList.add(IconType.SOCIAL_DESIGNER_NEWS);
		socialIconsDataList.add(IconType.SOCIAL_DEVIANT_ART);
		socialIconsDataList.add(IconType.SOCIAL_DIGG);
		socialIconsDataList.add(IconType.SOCIAL_DRIBBBLE);
		socialIconsDataList.add(IconType.SOCIAL_DRIVE);
		socialIconsDataList.add(IconType.SOCIAL_DROPBOX);
		socialIconsDataList.add(IconType.SOCIAL_EVERNOTE);
		socialIconsDataList.add(IconType.SOCIAL_FACEBOOK);
		socialIconsDataList.add(IconType.SOCIAL_FLICKR);
		socialIconsDataList.add(IconType.SOCIAL_FORRST);
		socialIconsDataList.add(IconType.SOCIAL_FOURSQUARE);
		socialIconsDataList.add(IconType.SOCIAL_GAME_CENTER);
		socialIconsDataList.add(IconType.SOCIAL_GITHUB);
		socialIconsDataList.add(IconType.SOCIAL_GOOGLE_PLUS);
		socialIconsDataList.add(IconType.SOCIAL_HACKER_NEWS);
		socialIconsDataList.add(IconType.SOCIAL_HI5);
		socialIconsDataList.add(IconType.SOCIAL_INSTAGRAM);
		socialIconsDataList.add(IconType.SOCIAL_JOOMLA);
		socialIconsDataList.add(IconType.SOCIAL_LASTFM);
		socialIconsDataList.add(IconType.SOCIAL_MYSPACE);
		socialIconsDataList.add(IconType.SOCIAL_ORKUT);
		socialIconsDataList.add(IconType.SOCIAL_PATH);
		socialIconsDataList.add(IconType.SOCIAL_PICASA);
		socialIconsDataList.add(IconType.SOCIAL_PINTEREST);
		socialIconsDataList.add(IconType.SOCIAL_RDIO);
		socialIconsDataList.add(IconType.SOCIAL_REDDIT);
		socialIconsDataList.add(IconType.SOCIAL_SKYPE);
		socialIconsDataList.add(IconType.SOCIAL_SKILLSHARE);
		socialIconsDataList.add(IconType.SOCIAL_SMASHING_MAG);
		socialIconsDataList.add(IconType.SOCIAL_SNAPCHAT);
		socialIconsDataList.add(IconType.SOCIAL_SPOTIFY);
		socialIconsDataList.add(IconType.SOCIAL_SQUIDOO);
		socialIconsDataList.add(IconType.SOCIAL_STACK_OVERFLOW);
		socialIconsDataList.add(IconType.SOCIAL_STEAM);
		socialIconsDataList.add(IconType.SOCIAL_STUMBLEUPON);
		socialIconsDataList.add(IconType.SOCIAL_TREEHOUSE);
		socialIconsDataList.add(IconType.SOCIAL_TUMBLR);
		socialIconsDataList.add(IconType.SOCIAL_TWITTER);
		socialIconsDataList.add(IconType.SOCIAL_VIMEO);
		socialIconsDataList.add(IconType.SOCIAL_WINDOWS);
		socialIconsDataList.add(IconType.SOCIAL_XBOX);
		socialIconsDataList.add(IconType.SOCIAL_YAHOO);
		socialIconsDataList.add(IconType.SOCIAL_YELP);
		socialIconsDataList.add(IconType.SOCIAL_YOUTUBE);
		socialIconsDataList.add(IconType.SOCIAL_ZERPLY);
		socialIconsDataList.add(IconType.SOCIAL_ZURB);
		add(createBlockGrid("socialIcons", optionsList, socialIconsDataList));

		ArrayList<IconType> miscIconsDataList = new ArrayList<>();
		miscIconsDataList.add(IconType.COMPASS);
		miscIconsDataList.add(IconType.MUSIC);
		miscIconsDataList.add(IconType.LIGHTBULB);
		miscIconsDataList.add(IconType.BATTERY_FULL);
		miscIconsDataList.add(IconType.BATTERY_HALF);
		miscIconsDataList.add(IconType.BATTERY_EMPTY);
		miscIconsDataList.add(IconType.PROJECTION_SCREEN);
		miscIconsDataList.add(IconType.INFO);
		miscIconsDataList.add(IconType.POWER);
		miscIconsDataList.add(IconType.ASTERISK);
		miscIconsDataList.add(IconType.AT_SIGN);
		miscIconsDataList.add(IconType.KEY);
		miscIconsDataList.add(IconType.TICKET);
		miscIconsDataList.add(IconType.BOOK);
		miscIconsDataList.add(IconType.BOOK_BOOKMARK);
		miscIconsDataList.add(IconType.ANCHOR);
		miscIconsDataList.add(IconType.PUZZLE);
		miscIconsDataList.add(IconType.FOOT);
		miscIconsDataList.add(IconType.PAW);
		miscIconsDataList.add(IconType.MOUNTAINS);
		miscIconsDataList.add(IconType.FIRST_AID);
		miscIconsDataList.add(IconType.TROPHY);
		miscIconsDataList.add(IconType.PROHIBITED);
		miscIconsDataList.add(IconType.NO_DOGS);
		miscIconsDataList.add(IconType.NO_SMOKING);
		miscIconsDataList.add(IconType.SAFETY_CONE);
		miscIconsDataList.add(IconType.SHIELD);
		miscIconsDataList.add(IconType.CROWN);
		miscIconsDataList.add(IconType.TARGET);
		miscIconsDataList.add(IconType.TARGET_TWO);
		miscIconsDataList.add(IconType.DIE_ONE);
		miscIconsDataList.add(IconType.DIE_TWO);
		miscIconsDataList.add(IconType.DIE_THREE);
		miscIconsDataList.add(IconType.DIE_FOUR);
		miscIconsDataList.add(IconType.DIE_FIVE);
		miscIconsDataList.add(IconType.DIE_SIX);
		miscIconsDataList.add(IconType.SKULL);
		miscIconsDataList.add(IconType.MAP);
		miscIconsDataList.add(IconType.CLIPBOARD);
		miscIconsDataList.add(IconType.CLIPBOARD_PENCIL);
		miscIconsDataList.add(IconType.CLIPBOARD_NOTES);
		add(createBlockGrid("miscIcons", optionsList, miscIconsDataList));
	}
	
	FoundationBlockGrid<IconType> createBlockGrid(String id, List<BlockGridOptions> optionsList, List<IconType> dataList) {
		return new FoundationBlockGrid<IconType>(id, optionsList, dataList) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer createContent(int idx, String id,
					IModel<IconType> model) {
				return new FoundationIconAndTextPanel(id, model.getObject(), StringUtil.EnumNameToCssClassName(model.getObject().name()));
			}
		};
	}
}