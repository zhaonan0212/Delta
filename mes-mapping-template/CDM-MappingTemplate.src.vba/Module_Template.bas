Sub RefreshTemplateData(xlWorkbookOld As Workbook, xlWorkbookNew As Workbook)
    ' Copy old template data to new template data
    
    Dim xlWorksheetOld1 As Worksheet
    Dim xlWorksheetOld2 As Worksheet
    Dim xlWorksheetNew1 As Worksheet
    Dim xlWorksheetNew2 As Worksheet
    Dim i As Integer
    Dim j As Integer
        
    'On Error GoTo Error_Control
    
    Set xlWorksheetOldTableInfo = xlWorkbookOld.Sheets("TableInfo")
    Set xlWorksheetOldColumnInfo = xlWorkbookOld.Sheets("ColumnInfo")
    Set xlWorksheetNewTableInfo = xlWorkbookNew.Sheets("TableInfo")
    Set xlWorksheetNewColumnInfo = xlWorkbookNew.Sheets("ColumnInfo")
    
    
    ' Refresh TableInfo
    i = 4
    j = 2
    With xlWorksheetNewTableInfo
                      

        With .Range(.Cells(4, 2), .Cells(5000, 26))
            .Value = ""
        End With
        
        While Trim(xlWorksheetOldTableInfo.Cells(i, 1).Value) <> ""
            For j = 2 To 26
                .Cells(i, j).Value = Trim(IIf(IsError(xlWorksheetOldTableInfo.Cells(i, j).Value), "", xlWorksheetOldTableInfo.Cells(i, j).Value))
            Next j
            i = i + 1
        Wend
        
    End With
    
    ' Refresh ColumnInfo
    i = 4
    j = 2
    With xlWorksheetNewColumnInfo
                  
        With .Range(.Cells(4, 2), .Cells(15000, 29))
            .Value = ""
        End With
        
        While Trim(xlWorksheetOldColumnInfo.Cells(i, 1).Value) <> ""
            For j = 2 To 29
                .Cells(i, j).Value = Trim(IIf(IsError(xlWorksheetOldColumnInfo.Cells(i, j).Value), "", xlWorksheetOldColumnInfo.Cells(i, j).Value))
            Next j
            i = i + 1
        Wend
    
    End With

    Exit Sub
    
'Error_Control:
'
'    Debug.Print Err.Number & "-" & Err.Description
'
'    Resume Next
End Sub

Sub RefreshTemplate()
    Dim xlExcel As New Excel.Application
    Dim xlWorkbook As Workbook
    Dim xlFileName As String
    Dim scTemplateVersion As String
    Dim tgTemplateVersion As String
    Dim scTemplateVersionArray() As String
    Dim tgTemplateVersionArray() As String
    Dim scTemplateName As String
    Dim scTemplateVer As String
    Dim tgTemplateName As String
    Dim tgTemplateVer As String
    
    xlFileName = Application.GetOpenFilename(FileFilter:="Excel Workbooks (*.xls*),*.xls*", Title:="Please choose the Mapping Template File for Import")
    If UCase(xlFileName) <> "FALSE" Then
        Set xlWorkbook = xlExcel.Workbooks.Open(xlFileName)
            
        scTemplateVersion = Trim(xlWorkbook.Worksheets("TemplateVersion").Cells(3, 3).Value)
        tgTemplateVersion = Trim(Worksheets("TemplateVersion").Cells(3, 3).Value)
        
        
        scTemplateVersionArray = Split(scTemplateVersion, "-")
        tgTemplateVersionArray = Split(tgTemplateVersion, "-")
    
        scTemplateName = scTemplateVersionArray(0) & scTemplateVersionArray(1)
        scTemplateVer = scTemplateVersionArray(2)
        
        
        tgTemplateName = tgTemplateVersionArray(0) & tgTemplateVersionArray(1)
        tgTemplateVer = tgTemplateVersionArray(2)
    
        
        If tgTemplateVersion >= scTemplateVersion Then
            Call RefreshTemplateData(xlWorkbook, ThisWorkbook)
            MsgBox "Load External Template Data Completed."
        Else
            MsgBox "Target Teplate Version (" & tgTemplateVersion & ")need higher than or equal than source Template Version(" & scTemplateVersion & ")"
        End If
        
        xlWorkbook.Close False
               
    Else
        MsgBox "You clicked Cancel Button and your data has not been modified, no worry. "
    End If
    
End Sub
