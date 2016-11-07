using Microsoft.Office.Interop.Word;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
/// <summary>
/// A simple hello Word program that created a Hello Word Word document using Microsofts free libraries
/// 
/// Right click onRefreneces->Add Reference->Browse for the included dll to use this in other projects
/// 
/// Ref: https://support.microsoft.com/en-gb/kb/316384
/// See also: https://msdn.microsoft.com/en-us/library/kw65a0we.aspx
/// </summary>
/// 
namespace MSWordWriter
{
    class Program
    {
        static void Main(string[] args)
        {
            object oMissing = System.Reflection.Missing.Value;
            object oEndOfDoc = "\\endofdoc"; /* \endofdoc is a predefined bookmark */

            string path = Directory.GetCurrentDirectory()+ "\\data\\";///put any path here
            Application app = new Application();
            Documents docs = app.Documents;

            docs.Add();// two variables for convinience
            Document doc = app.ActiveDocument;

            object start = 0;
            object end = 0;

            Paragraph parag;
            parag = doc.Content.Paragraphs.Add(ref oMissing);
            parag.Format.set_Style("Title");//Be sure to apply this first or it will override other style settings such as underline, or SpaceAfter
            parag.Range.Text = "Hello Word!";
            parag.Range.Font.Bold = 1;
            parag.Range.Font.Underline = WdUnderline.wdUnderlineWavyDouble;
            parag.Format.SpaceAfter = 32;    //24 pt spacing after paragraph.
            parag.Range.InsertParagraphAfter();//adds newLine

            Range rng = doc.Bookmarks.get_Item(ref oEndOfDoc).Range;//sets the cursor to the end of the document
            app.Selection.Start = rng.Start;//need to set the cursor of the app.Selection to the end
            app.Selection.set_Style("Normal");
            try
            {
                app.Selection.InlineShapes.AddPicture(path + "cover.jpg");
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            app.Selection.Range.InsertParagraphAfter();

            rng = doc.Bookmarks.get_Item(ref oEndOfDoc).Range;//sets the cursor to the end of the document
            parag = doc.Content.Paragraphs.Add(rng);
            parag.Range.Text = "Hello Word, now in a table, on next page:";
            parag.Format.SpaceAfter = 6;
            parag.Range.InsertParagraphAfter();
            parag.Range.InsertBreak(WdBreakType.wdPageBreak);//insert page break (ctrl+enter)


            rng = doc.Bookmarks.get_Item(ref oEndOfDoc).Range;
            Table table = doc.Tables.Add(rng, 1, 2, ref oMissing, ref oMissing);//create a 1-by-2 table
            table.Range.ParagraphFormat.SpaceAfter = 6;
            table.TopPadding = 5;
            table.Borders.OutsideLineStyle = WdLineStyle.wdLineStyleDashDot;//set some doodads 
            table.Borders.InsideColor = WdColor.wdColorDarkRed;
            table.Borders.InsideLineStyle = WdLineStyle.wdLineStyleDashDotStroked;
            table.Cell(1, 1).Range.Shading.BackgroundPatternColor = WdColor.wdColorAqua;
            table.Columns[2].Shading.BackgroundPatternColor = WdColor.wdColorBrightGreen;

            table.Cell(1, 1).Range.Text = "Hello";
            table.Cell(1, 2).Range.Text = "Word!";
            table.Rows[1].Range.Font.Bold = 1;
            table.Rows[1].Range.Font.Italic = 1;
            table.Columns[1].Width = app.InchesToPoints(2); //Change width of columns 1 & 2
            table.Columns[2].Width = app.InchesToPoints(5);

            ///Now using the same document, get only the text out


            try
            {
                //doc.Save();  //Opens a Save as file dialog  
                doc.SaveAs2(path + "HelloWord1.docx");
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            try
            {
                app.ActiveDocument.Close();//if the program crashes before this point it is likely the doc will be stuck in an open state
            }
            catch
            {
                Console.WriteLine("Save cancelled");
            }


            ///read the words from the .docx and put the raw data (not including img, etc) to a .txt file
            try
            {
                docs.Open(path + "HelloWord1.docx");
                doc = app.ActiveDocument;//im not 100% positive this behaves as intended
                string allWords = doc.Content.Text;

                //writer is displosed of after the scope ends
                try
                {
                    using (BinaryWriter writer = new BinaryWriter(File.Open(path + "helloWord.txt", FileMode.Append)))
                    {
                        writer.Write(allWords);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine("Error using binary writer. Please check if you have write permissions\n", e.Message);
                }
            }catch (Exception e){
                Console.WriteLine("Could not open the file that has been created. Please check if you have write permissions\n");
            }

            app.Quit();

            Console.WriteLine("Finished. Press any key to exit...");
            Console.ReadKey();
        }
    }
}
