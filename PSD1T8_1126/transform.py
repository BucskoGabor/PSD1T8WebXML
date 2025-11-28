import lxml.etree as ET
import sys
import os

def transform_xml(xml_file, xsl_file, output_file):
    """Transform XML using XSLT and save to output file"""
    try:
        # Parse XML and XSLT
        xml_doc = ET.parse(xml_file)
        xsl_doc = ET.parse(xsl_file)
        
        # Create transformer
        transform = ET.XSLT(xsl_doc)
        
        # Transform
        result = transform(xml_doc)
        
        # Write result
        with open(output_file, 'wb') as f:
            f.write(ET.tostring(result, pretty_print=True, encoding='UTF-8', xml_declaration=True))
        
        print(f"✓ Successfully created: {output_file}")
        return True
    except Exception as e:
        print(f"✗ Error transforming {xml_file}: {e}")
        return False

if __name__ == "__main__":
    # Define transformations
    transformations = [
        # Task 1 - Student data
        ("hallgatoPSD1T8.xml", "hallgatoPSD1T8.xsl", "hallgatoPSD1T8.html"),
        
        # Task 2 - Timetable
        ("orarendPSD1T8.xml", "orarendPSD1T8.xsl", "orarendPSD1T8.html"),
        
        # Task 3 - Cars
        ("autokPSD1T8.xml", "autok1PSD1T8.xsl", "autok1PSD1T8.html"),
        ("autokPSD1T8.xml", "autok2PSD1T8.xsl", "autok2PSD1T8.html"),
        ("autokPSD1T8.xml", "autok3PSD1T8.xsl", "autok3PSD1T8.html"),
        ("autokPSD1T8.xml", "autok4PSD1T8.xsl", "autok4PSD1T8.html"),
        ("autokPSD1T8.xml", "autok5PSD1T8.xsl", "autok5PSD1T8.html"),
        ("autokPSD1T8.xml", "autok6PSD1T8.xsl", "autok6PSD1T8.html"),
        ("autokPSD1T8.xml", "autok7PSD1T8.xsl", "autok7PSD1T8.html"),
        ("autokPSD1T8.xml", "autok8PSD1T8.xsl", "autok8PSD1T8.xml"),
    ]
    
    print("Starting XSLT transformations...\n")
    
    success_count = 0
    for xml_file, xsl_file, output_file in transformations:
        if transform_xml(xml_file, xsl_file, output_file):
            success_count += 1
    
    print(f"\n{success_count}/{len(transformations)} transformations completed successfully!")
