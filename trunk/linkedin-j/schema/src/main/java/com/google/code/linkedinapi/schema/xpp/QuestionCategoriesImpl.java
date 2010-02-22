
package com.google.code.linkedinapi.schema.xpp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import com.google.code.linkedinapi.schema.QuestionCategories;
import com.google.code.linkedinapi.schema.QuestionCategory;

public class QuestionCategoriesImpl
	extends BaseSchemaEntity
    implements QuestionCategories
{

    private final static long serialVersionUID = 2461660169443089969L;
    protected List<QuestionCategory> questionCategoryList;
    protected Long total;

    public List<QuestionCategory> getQuestionCategoryList() {
        if (questionCategoryList == null) {
            questionCategoryList = new ArrayList<QuestionCategory>();
        }
        return this.questionCategoryList;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long value) {
        this.total = value;
    }
    
	@Override
	public void init(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, null);
		setTotal(XppUtils.getAttributeValueAsLongFromNode(parser, "total"));

        while (parser.nextTag() == XmlPullParser.START_TAG) {
        	String name = parser.getName();
        	
        	if (name.equals("question-category")) {
    			QuestionCategoryImpl categoryImpl = new QuestionCategoryImpl();
    			categoryImpl.init(parser);
    			getQuestionCategoryList().add(categoryImpl);
            } else {
                // Consume something we don't understand.
            	System.err.println(getClass().getName() + ":Found tag that we don't recognize: " + name);
            	XppUtils.skipSubTree(parser);
            }
        }
	}

	@Override
	public void toXml(XmlSerializer serializer) throws IOException {
		XmlSerializer element = serializer.startTag(null, "question-categories");
		XppUtils.setAttributeValueToNode(element, "total", getTotal());
		for (QuestionCategory category : getQuestionCategoryList()) {
			((QuestionCategoryImpl) category).toXml(serializer);
		}
		serializer.endTag(null, "question-categories");
	}
}